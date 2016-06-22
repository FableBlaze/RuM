package ee.ut.cs.rum.scheduler.util;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.scheduler.internal.Activator;
import ee.ut.cs.rum.scheduler.internal.task.RumJob;

public final class RumScheduler {

	private RumScheduler() {
	}

	public static void scheduleTask(Long subTaskId) {
		Scheduler scheduler = Activator.getScheduler();

		String rumJobName = "RumJob"+subTaskId.toString();
		JobDetail job = JobBuilder.newJob(RumJob.class).withIdentity(rumJobName, "RumJobs").build();
		job.getJobDataMap().put(RumJob.TASK_ID, subTaskId);

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(rumJobName, "RumJobs").startNow().build();

		try {
			scheduler.scheduleJob(job, trigger);
			Activator.getLogger().info("Added task to queue: " + subTaskId.toString() + " (" +rumJobName + ")");
		} catch (SchedulerException e) {
			Activator.getLogger().info("Failed scheduling task: " + subTaskId.toString() + " (" +rumJobName + ")");
			e.printStackTrace();
		}
	}

	public static void setRumController(RumController rumController) {
		Activator.setRumController(rumController);
	}
}
