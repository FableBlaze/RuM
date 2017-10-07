package ee.ut.cs.rum.scheduler.internal.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.SubTaskDependency;
import ee.ut.cs.rum.database.domain.UserAccount;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.domain.UserFileType;
import ee.ut.cs.rum.database.domain.enums.SystemParametersEnum;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.domain.enums.SubTaskStatus;
import ee.ut.cs.rum.database.util.SystemParameterAccess;
import ee.ut.cs.rum.database.util.UserAccountAccess;
import ee.ut.cs.rum.database.util.exceptions.SystemParameterNotSetException;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.plugins.configuration.util.PluginUtils;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.PluginOutput;
import ee.ut.cs.rum.plugins.development.interfaces.RumPluginFactory;
import ee.ut.cs.rum.plugins.development.interfaces.factory.RumPluginWorker;
import ee.ut.cs.rum.scheduler.internal.Activator;
import ee.ut.cs.rum.scheduler.internal.util.SubTasksData;
import ee.ut.cs.rum.scheduler.internal.util.TasksData;
import ee.ut.cs.rum.scheduler.util.RumScheduler;

public class RumJob implements Job {
	public static final String SUB_TASK_ID = "subTaskId";
	
	private SubTask subTask;
	private PluginOutput[] rumJobTaskOutputs;
	UserAccount systemUserAccount;

	public RumJob() {
		systemUserAccount = UserAccountAccess.getSystemUserAccount();
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		Long subTaskId = context.getJobDetail().getJobDataMap().getLong(SUB_TASK_ID);

		try {
			subTask = SubTasksData.updateSubTaskStatusInDb(subTaskId, SubTaskStatus.STARTING, systemUserAccount);
			Plugin plugin = subTask.getPlugin();
			Bundle rumJobPluginBundle = findSelectedPluginBundle(plugin);

			if (rumJobPluginBundle==null || rumJobPluginBundle.getRegisteredServices()==null) {
				rumJobPluginBundle = installSelectedPluginBundle(plugin);
			}
			
			String task_results_root_asString = SystemParameterAccess.getSystemParameterValue(SystemParametersEnum.TASK_RESULTS_ROOT);
			File task_results_root = new File(task_results_root_asString);
			File outputDirectory = new File(task_results_root, subTask.getId() + "_" + new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(subTask.getCreatedAt()));
			subTask = SubTasksData.updateSubTaskOutputPathInDb(subTaskId, outputDirectory.getPath(), systemUserAccount);

			RumPluginFactory rumJobPluginFactory = findRumPluginFactoryService(rumJobPluginBundle);
			RumPluginWorker rumJobPluginWorker = rumJobPluginFactory.createRumPluginWorker();
			
			if (outputDirectory.mkdir()) {
				SubTasksData.updateSubTaskStatusInDb(subTaskId, SubTaskStatus.RUNNING, systemUserAccount);
				if (subTask.getTask().getStatus() == TaskStatus.NEW || subTask.getTask().getStatus() == TaskStatus.QUEUED) {
					TasksData.updateTaskStatusInDb(subTask.getTask().getId(), TaskStatus.STARTED, systemUserAccount);
				}
				Activator.getLogger().info("RumJob started: " + jobKey + " executing at " + new Date());
				
				int rumJobResult = rumJobPluginWorker.runWork(subTask.getConfigurationValues(), outputDirectory);
				Activator.getLogger().info("RumJobResult toString: " + Integer.toString(rumJobResult));
				
				if (rumJobResult==0) {
					SubTasksData.updateSubTaskStatusInDb(subTaskId, SubTaskStatus.DONE, systemUserAccount);
				} else {
					SubTasksData.updateSubTaskStatusInDb(subTaskId, SubTaskStatus.FAILED, systemUserAccount);
				}
				PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(plugin);
				rumJobTaskOutputs = pluginInfo.getOutputs();
				addTaskCreatedFilesToDb(outputDirectory);
				
				for (SubTaskDependency subTaskDependency : subTask.getFulfilledDependencies()) {
					RumScheduler.scheduleTask(subTaskDependency.getRequiredBySubTask().getTask().getId());
				}
			}			
			
			Activator.getLogger().info("RumJob done: " + jobKey + " at " + new Date());
		} catch (SystemParameterNotSetException e) {
			SubTasksData.updateSubTaskStatusInDb(subTaskId, SubTaskStatus.FAILED, systemUserAccount);
			Activator.getLogger().info("RumJob failed: " + jobKey + " at " + new Date() + " " + e.toString());
		} catch (Exception e) {
			SubTasksData.updateSubTaskStatusInDb(subTaskId, SubTaskStatus.FAILED, systemUserAccount);
			Activator.getLogger().info("RumJob failed: " + jobKey + " at " + new Date() + " " + e.toString());
		}
	}


	private Bundle findSelectedPluginBundle(Plugin rumJobPlugin) {
		for (Bundle bundle : Activator.getContext().getBundles()) {
			if (bundle.getLocation().equals("file:///" + rumJobPlugin.getFileLocation())) {
				return bundle;
			}
		}
		return null;
	}

	private Bundle installSelectedPluginBundle(Plugin rumJobPlugin) {
		Bundle selectedPluginBundle = null;
		try {
			selectedPluginBundle = Activator.getContext().installBundle("file:///" + rumJobPlugin.getFileLocation());
			selectedPluginBundle.start();
		} catch (BundleException e) {
			Activator.getLogger().info("Failed loading plugin: " + rumJobPlugin.toString());
		}
		return selectedPluginBundle;
	}

	private RumPluginFactory findRumPluginFactoryService(Bundle rumJobPluginBundle) {
		for (ServiceReference<?> serviceReference : rumJobPluginBundle.getRegisteredServices()) {
			String[] objectClasses = (String[])serviceReference.getProperty("objectClass");
			for (String objectClass : objectClasses) {
				if (objectClass.equals(RumPluginFactory.class.getName())) {
					return (RumPluginFactory) rumJobPluginBundle.getBundleContext().getService(serviceReference);
				}
			}
		}
		return null;
	}

	public void addTaskCreatedFilesToDb(File directory) {
		
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				UserFile userFile = new UserFile();
				userFile.setOriginalFilename(file.getName());
				userFile.setPlugin(subTask.getPlugin());
				userFile.setTask(subTask.getTask());
				userFile.setSubTask(subTask);
				userFile.setProject(subTask.getTask().getProject());
				userFile.setFileLocation(file.getPath());
				
				for (PluginOutput rumJobTaskOutput : rumJobTaskOutputs) {
					if (rumJobTaskOutput.getFileName().equals(file.getName())) {
						List<UserFileType> userFileTypes = new ArrayList<UserFileType>();
						String[] fileTypes = rumJobTaskOutput.getFileTypes();
						for (String inputType : fileTypes) {
							UserFileType userFileType = new UserFileType();
							userFileType.setTypeName(inputType);
							userFileTypes.add(userFileType);
						}
						userFile.setUserFileTypes(userFileTypes);
						Activator.getLogger().info("Found plugin output file with types: " + rumJobTaskOutput.getFileTypes().toString());
					}
				}
				userFile = (UserFile)Activator.getRumController().changeData(ControllerUpdateType.CREATE, ControllerEntityType.USER_FILE, userFile, systemUserAccount);
			} else if (file.isDirectory()) {
				addTaskCreatedFilesToDb(file);
			}
		}
	}
}
