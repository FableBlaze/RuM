package ee.ut.cs.rum.scheduler.internal.util;

import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.UserAccount;
import ee.ut.cs.rum.database.domain.enums.SubTaskStatus;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.util.SubTaskAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.scheduler.internal.Activator;

public final class SubTasksData {
	private SubTasksData() {
	}
	
	public static SubTask updateSubTaskStatusInDb(Long subTaskId, SubTaskStatus subTaskStatus, UserAccount userAccount) {
		SubTask subTask = SubTaskAccess.getSubTaskDataFromDb(subTaskId);
		subTask.setStatus(subTaskStatus);
		
		subTask = (SubTask)Activator.getRumController().changeData(ControllerUpdateType.MODIFIY, ControllerEntityType.SUBTASK, subTask, userAccount);
		
		if (subTaskStatus==SubTaskStatus.DONE || subTaskStatus==SubTaskStatus.FAILED) {
			if (!subTask.getTask().getSubTasks().removeIf(st -> (st.getStatus()!=SubTaskStatus.DONE && st.getStatus()!=SubTaskStatus.FAILED))) {
				TasksData.updateTaskStatusInDb(subTask.getTask().getId(), TaskStatus.DONE, userAccount);
			}
		}
		
		return subTask;
	}
	
	public static SubTask setSubTaskToQueuing(Long subTaskId, String configurationValuesString, UserAccount userAccount) {
		
		SubTask subTask = SubTaskAccess.getSubTaskDataFromDb(subTaskId);
		subTask.setConfigurationValues(configurationValuesString);
		subTask.setStatus(SubTaskStatus.QUEUING);
		subTask = (SubTask)Activator.getRumController().changeData(ControllerUpdateType.MODIFIY, ControllerEntityType.SUBTASK, subTask, userAccount);
		
		return subTask;
	}
	
	public static SubTask updateSubTaskOutputPathInDb(Long subTaskId, String outputPath, UserAccount userAccount) {
		SubTask subTask = SubTaskAccess.getSubTaskDataFromDb(subTaskId);
		subTask.setOutputPath(outputPath);
		
		subTask = (SubTask)Activator.getRumController().changeData(ControllerUpdateType.MODIFIY, ControllerEntityType.SUBTASK, subTask, userAccount);
		
		return subTask;
	}
}
