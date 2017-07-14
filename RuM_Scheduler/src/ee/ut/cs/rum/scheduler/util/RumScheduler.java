package ee.ut.cs.rum.scheduler.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.google.gson.Gson;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.SubTaskDependency;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.util.SubTaskAccess;
import ee.ut.cs.rum.database.util.UserFileAccess;
import ee.ut.cs.rum.scheduler.internal.Activator;
import ee.ut.cs.rum.scheduler.internal.task.RumJob;
import ee.ut.cs.rum.scheduler.internal.util.SubTasksData;

public final class RumScheduler {

	private RumScheduler() {
	}

	public static void scheduleTask(Long taskId) {
		Scheduler scheduler = Activator.getScheduler();

		List<SubTask> subTasks = SubTaskAccess.getTaskSubtasksDataFromDb(taskId);

		for (SubTask subTask : subTasks) {
			Long subTaskId = subTask.getId();
			if (subTask.getStatus()==TaskStatus.NEW || subTask.getStatus()==TaskStatus.WAITING) {
				if (processSubTaskDependencies(subTask)) {
					SubTasksData.updateSubTaskStatusInDb(subTaskId, TaskStatus.QUEUING);
					String rumJobName = "RumJob"+subTaskId.toString();

					JobDetail job = JobBuilder.newJob(RumJob.class).withIdentity(rumJobName, "RumJobs").build();
					job.getJobDataMap().put(RumJob.SUB_TASK_ID, subTaskId);

					Trigger trigger = TriggerBuilder.newTrigger().withIdentity(rumJobName, "RumJobs").startNow().build();

					try {
						scheduler.scheduleJob(job, trigger);
						Activator.getLogger().info("Added task to queue: " + subTaskId.toString() + " (" +rumJobName + ")");
						SubTasksData.updateSubTaskStatusInDb(subTaskId, TaskStatus.QUEUED);
					} catch (SchedulerException e) {
						Activator.getLogger().info("Failed scheduling task: " + subTaskId.toString() + " (" +rumJobName + ")" + e.toString());
						SubTasksData.updateSubTaskStatusInDb(subTaskId, TaskStatus.FAILED);
					} catch (Exception e) {
						Activator.getLogger().info("General task scheduling error: " + subTaskId.toString() + " (" +rumJobName + ")" + e.toString());
						SubTasksData.updateSubTaskStatusInDb(subTaskId, TaskStatus.FAILED);
					}
				} else if (subTask.getStatus()==TaskStatus.NEW) {
					SubTasksData.updateSubTaskStatusInDb(subTaskId, TaskStatus.WAITING);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static boolean processSubTaskDependencies(SubTask subTask) {
		boolean dependenciesOk=true;

		if (!subTask.getRequiredDependencies().isEmpty()) {
			Gson gson = new Gson();
			Map<String,String> configurationValues = new HashMap<String,String>();
			configurationValues = gson.fromJson(subTask.getConfigurationValues(), configurationValues.getClass());

			for (SubTaskDependency subTaskDependency : subTask.getRequiredDependencies()) {
				if (subTaskDependency.getFulfilledBySubTask().getStatus()!=TaskStatus.DONE || subTaskDependency.getFulfilledBySubTask().getStatus()!=TaskStatus.FAILED) {
					//Allowing FAILED status, because even if the subTask fails, it may still have created the needed output 
					boolean fileOk=false;
					for (UserFile userFile : UserFileAccess.getSubTaskUserFilesDataFromDb(subTaskDependency.getFulfilledBySubTask().getId())) {
						if (userFile.getOriginalFilename().equals(subTaskDependency.getFileName())) {
							configurationValues.replace(subTaskDependency.getRequiredForParameter(), userFile.getFileLocation());
							fileOk=true;
							break;
						}
					}
					if (!fileOk) {
						dependenciesOk=false;
						break;
					}
				} else {
					dependenciesOk=false;
					break;
				}
			}

			if (dependenciesOk) {
				String configurationValuesString = gson.toJson(configurationValues);
				SubTasksData.updateSubTaskConfigurationValuesInDb(subTask.getId(), configurationValuesString);
			}
		}
		return dependenciesOk;
	}

	public static void setRumController(RumController rumController) {
		Activator.setRumController(rumController);
	}
}
