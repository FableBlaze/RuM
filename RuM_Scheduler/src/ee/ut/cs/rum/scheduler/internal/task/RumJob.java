package ee.ut.cs.rum.scheduler.internal.task;

import java.io.File;
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
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.domain.UserFileType;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.util.PluginAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.plugins.configuration.util.PluginUtils;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.PluginOutput;
import ee.ut.cs.rum.plugins.development.interfaces.RumPluginFactory;
import ee.ut.cs.rum.plugins.development.interfaces.factory.RumPluginWorker;
import ee.ut.cs.rum.scheduler.internal.Activator;
import ee.ut.cs.rum.scheduler.internal.util.TasksData;

public class RumJob implements Job {
	public static final String TASK_ID = "taskId";
	
	private Task rumJobTask;
	private PluginOutput[] rumJobTaskOutputs;

	public RumJob() {
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		Long taskId = context.getJobDetail().getJobDataMap().getLong(TASK_ID);

		try {
			rumJobTask = TasksData.updateTaskStatusInDb(taskId, TaskStatus.STARTING);
			Plugin rumJobPlugin = PluginAccess.getPluginDataFromDb(rumJobTask.getPluginId());
			Bundle rumJobPluginBundle = findSelectedPluginBundle(rumJobPlugin);

			if (rumJobPluginBundle==null) {
				rumJobPluginBundle = installSelectedPluginBundle(rumJobPlugin);
			}

			RumPluginFactory rumJobPluginFactory = findRumPluginFactoryService(rumJobPluginBundle);
			RumPluginWorker rumJobPluginWorker = rumJobPluginFactory.createRumPluginWorker();

			TasksData.updateTaskStatusInDb(taskId, TaskStatus.RUNNING);
			Activator.getLogger().info("RumJob started: " + jobKey + " executing at " + new Date());

			int rumJobResult = rumJobPluginWorker.runWork(rumJobTask.getConfigurationValues(), new File(rumJobTask.getOutputPath()));
			Activator.getLogger().info("RumJobResult toString: " + Integer.toString(rumJobResult));
			
			if (rumJobResult==0) {
				TasksData.updateTaskStatusInDb(taskId, TaskStatus.DONE);
			} else {
				TasksData.updateTaskStatusInDb(taskId, TaskStatus.FAILED);
			}
			
			PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(rumJobPlugin);
			rumJobTaskOutputs = pluginInfo.getOutputs();
			addTaskCreatedFilesToDb(rumJobTask.getOutputPath());
			
			Activator.getLogger().info("RumJob done: " + jobKey + " at " + new Date());
		} catch (Exception e) {
			TasksData.updateTaskStatusInDb(taskId, TaskStatus.FAILED);
			Activator.getLogger().info("RumJob failed: " + jobKey + " at " + new Date());
			e.printStackTrace();
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

	public void addTaskCreatedFilesToDb(String directoryPath) {
		File directory = new File(directoryPath);

		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				UserFile userFile = new UserFile();
				userFile.setOriginalFilename(file.getName());
				userFile.setCreatedByUserId("TODO");
				userFile.setCreatedByPluginId(rumJobTask.getPluginId());
				userFile.setCreatedAt(new Date());
				userFile.setTaskId(rumJobTask.getId());
				userFile.setProjectId(rumJobTask.getProjectId());
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
				userFile = (UserFile)Activator.getRumController().changeData(ControllerUpdateType.CREATE, ControllerEntityType.USER_FILE, userFile);
			} else if (file.isDirectory()) {
				addTaskCreatedFilesToDb(file.getAbsolutePath());
			}
		}
	}
}
