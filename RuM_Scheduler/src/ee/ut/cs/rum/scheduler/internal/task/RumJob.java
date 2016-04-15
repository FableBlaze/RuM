package ee.ut.cs.rum.scheduler.internal.task;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import ee.ut.cs.rum.database.domain.TaskStatusEnum;
import ee.ut.cs.rum.scheduler.internal.Activator;
import ee.ut.cs.rum.scheduler.internal.util.TasksData;

public class RumJob implements Job {
	public static final String TASK_ID = "taskId";
	
	public RumJob() {
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobKey jobKey = context.getJobDetail().getKey();
		
		Long taskId = context.getJobDetail().getJobDataMap().getLong(TASK_ID);
		
		TasksData.updateTaskStatusInDb(taskId, TaskStatusEnum.RUNNING);
		Activator.getLogger().info("RumJob started: " + jobKey + " executing at " + new Date());
		try {
			Thread.sleep(65L * 1000L);
		} catch (InterruptedException e) {
			TasksData.updateTaskStatusInDb(taskId, TaskStatusEnum.FAILED);
			e.printStackTrace();
		}
		TasksData.updateTaskStatusInDb(taskId, TaskStatusEnum.DONE);
		Activator.getLogger().info("RumJob done: " + jobKey + " executing at " + new Date());
	}
}
