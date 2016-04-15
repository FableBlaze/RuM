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
		String rumJobName = "RumJob"+taskId.toString();
		JobDetail job = JobBuilder.newJob(RumJob.class).withIdentity(rumJobName, "RumJobs").build();
		job.getJobDataMap().put(RumJob.TASK_ID, taskId);

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(rumJobName, "RumJobs").startNow().build();
		
		try {
			scheduler.scheduleJob(job, trigger);
			Activator.getLogger().info("Added task to queue: " + taskId.toString());
		} catch (SchedulerException e) {
			Activator.getLogger().info("Failed scheduling task: " + taskId.toString());
			e.printStackTrace();
		}
		
	}
}
