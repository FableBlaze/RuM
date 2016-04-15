package ee.ut.cs.rum.scheduler.internal.task;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import ee.ut.cs.rum.scheduler.internal.Activator;

public class RumJob implements Job {
	public RumJob() {
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		Activator.getLogger().info("RumJob started: " + jobKey + " executing at " + new Date());
		try {
			Thread.sleep(65L * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Activator.getLogger().info("RumJob done: " + jobKey + " executing at " + new Date());
	}
}
