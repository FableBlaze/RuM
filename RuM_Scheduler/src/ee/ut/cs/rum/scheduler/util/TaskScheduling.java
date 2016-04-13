package ee.ut.cs.rum.scheduler.util;

import ee.ut.cs.rum.scheduler.internal.Activator;

public final class TaskScheduling {
	
	private TaskScheduling() {
	}
	
	public static void scheduleTask(Long taskId) {
		//TODO: Scheduling the task
		Activator.getLogger().info("Added task to queue: " + taskId.toString());
	}
}
