package ee.ut.cs.rum.scheduler.util;

import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.SubTaskDependency;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.util.SubTaskAccess;
import ee.ut.cs.rum.scheduler.internal.Activator;
import ee.ut.cs.rum.scheduler.internal.task.RumJob;

public final class RumScheduler {

	private RumScheduler() {
	}

	public static void scheduleTask(Long taskId) {
		Scheduler scheduler = Activator.getScheduler();
		
		List<SubTask> subTasks = SubTaskAccess.getTaskSubtasksDataFromDb(taskId);
		
		for (SubTask subTask : subTasks) {
			if (processSubTaskDependencies(subTask)) {
				Long subTaskId = subTask.getId();
				String rumJobName = "RumJob"+subTaskId.toString();
				JobDetail job = JobBuilder.newJob(RumJob.class).withIdentity(rumJobName, "RumJobs").build();
				job.getJobDataMap().put(RumJob.TASK_ID, subTaskId);
				
				Trigger trigger = TriggerBuilder.newTrigger().withIdentity(rumJobName, "RumJobs").startNow().build();
				
				try {
					scheduler.scheduleJob(job, trigger);
					Activator.getLogger().info("Added task to queue: " + subTaskId.toString() + " (" +rumJobName + ")");
				} catch (SchedulerException e) {
					Activator.getLogger().info("Failed scheduling task: " + subTaskId.toString() + " (" +rumJobName + ")" + e.toString());
				} catch (Exception e) {
					Activator.getLogger().info("General task scheduling error: " + subTaskId.toString() + " (" +rumJobName + ")" + e.toString());
				}
			}
		}
	}
	
	private static boolean processSubTaskDependencies(SubTask subTask) {
		boolean dependenciesOk=true;
		for (SubTaskDependency subTaskDependency : subTask.getRequiredDependencies()) {
			if (subTaskDependency.getFulfilledBySubTask().getStatus()!=TaskStatus.DONE) {
				dependenciesOk=false;
			}
		}
		return dependenciesOk;
	}

	public static void setRumController(RumController rumController) {
		Activator.setRumController(rumController);
	}
}
