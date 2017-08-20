package ee.ut.cs.rum.scheduler.internal.util;

import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.util.SubTaskAccess;
import ee.ut.cs.rum.database.util.UserAccountAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.scheduler.internal.Activator;

public final class SubTasksData {
	private SubTasksData() {
	}
	
	public static SubTask updateSubTaskStatusInDb(Long subTaskId, TaskStatus taskStatus) {
		SubTask subTask = SubTaskAccess.getSubTaskDataFromDb(subTaskId);
		subTask.setStatus(taskStatus);
		
		//TODO: Should be real user
		subTask = (SubTask)Activator.getRumController().changeData(ControllerUpdateType.MODIFIY, ControllerEntityType.SUBTASK, subTask, UserAccountAccess.getSystemUserAccount());
		
		return subTask;
	}
	
	public static SubTask updateSubTaskConfigurationValuesInDb(Long subTaskId, String configurationValuesString) {
		SubTask subTask = SubTaskAccess.getSubTaskDataFromDb(subTaskId);
		subTask.setConfigurationValues(configurationValuesString);
		
		//TODO: Should be real user
		subTask = (SubTask)Activator.getRumController().changeData(ControllerUpdateType.MODIFIY, ControllerEntityType.SUBTASK, subTask, UserAccountAccess.getSystemUserAccount());
		
		return subTask;
	}
}
