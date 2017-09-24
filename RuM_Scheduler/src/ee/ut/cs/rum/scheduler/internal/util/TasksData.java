package ee.ut.cs.rum.scheduler.internal.util;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.UserAccount;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.scheduler.internal.Activator;

public final class TasksData {
	private TasksData() {
	}
	
	public static Task updateTaskStatusInDb(Long taskId, TaskStatus taskStatus, UserAccount userAccount) {
		Task task = TaskAccess.getTaskDataFromDb(taskId);
		task.setStatus(taskStatus);
		
		task = (Task)Activator.getRumController().changeData(ControllerUpdateType.MODIFIY, ControllerEntityType.TASK, task, userAccount);
		
		return task;
	}
}
