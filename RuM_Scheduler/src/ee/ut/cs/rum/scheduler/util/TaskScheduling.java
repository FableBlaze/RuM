package ee.ut.cs.rum.scheduler.util;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import ee.ut.cs.rum.scheduler.internal.Activator;
import ee.ut.cs.rum.scheduler.internal.task.RumJob;

public final class TaskScheduling {
	
	private TaskScheduling() {
	}
	
	public static void scheduleTask(Long taskId) {
		Scheduler scheduler = Activator.getScheduler();
		
		JobDetail job = JobBuilder.newJob(RumJob.class).withIdentity("RumJob"+taskId, "RumJobs").build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("RumJob"+taskId, "RumJobs").startNow().build();
		
		try {
			scheduler.scheduleJob(job, trigger);
			Activator.getLogger().info("Added task to queue: " + taskId.toString());
		} catch (SchedulerException e) {
			Activator.getLogger().info("Failed scheduling task: " + taskId.toString());
			e.printStackTrace();
		}
		
	}
}
