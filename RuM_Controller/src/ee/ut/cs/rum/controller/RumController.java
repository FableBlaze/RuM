package ee.ut.cs.rum.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ee.ut.cs.rum.controller.internal.Activator;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.util.ProjectAccess;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.database.util.UserFileAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class RumController {

	private List<RumUpdatableView> projectListeners;
	private List<RumUpdatableView> taskListeners;
	private List<RumUpdatableView> userFileListeners;

	public RumController() {
		taskListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
		projectListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
		userFileListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
	}

	public Object changeData(ControllerUpdateType controllerUpdateType, ControllerEntityType controllerEntityType, Object updatedEntity) {
		Activator.getLogger().info("changeData - updateType: " + controllerUpdateType + ", entityType: " + controllerEntityType + ", entity: " + updatedEntity.toString());
		switch (controllerEntityType) {
		case PROJECT:
			if (updatedEntity instanceof Project) {
				Project project = (Project)updatedEntity;
				updatedEntity = changeDataProject(controllerUpdateType, project);				
			}
			break;
		case TASK:
			if (updatedEntity instanceof Task) {
				Task task = (Task)updatedEntity;
				updatedEntity = changeDataTask(controllerUpdateType, task);				
			}
			break;
		case USER_FILE:
			if (updatedEntity instanceof UserFile) {
				UserFile userFile = (UserFile)updatedEntity;
				updatedEntity = changeDataUserFile(controllerUpdateType, userFile);				
			}
			break;
		default:
			break;
		}
		return updatedEntity;
	}

	private Project changeDataProject(ControllerUpdateType controllerUpdateType, Project project) {
		switch (controllerUpdateType) {
		case CREATE:
			project = ProjectAccess.addProjectDataToDb(project);
			break;
		case MODIFIY:
			project = ProjectAccess.updateProjectDataInDb(project);
			break;
		case DELETE:
			ProjectAccess.removeProjectDataFromDb(project);
			break;
		default:
			break;
		}
		final Project finalProject = project;
		synchronized (projectListeners) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					for (RumUpdatableView rumUpdatableView : projectListeners) {
						rumUpdatableView.controllerUpdateNotify(controllerUpdateType, finalProject);
					}
				}
			});  
			thread.start();
		}
		return finalProject;
	}
	
	private Task changeDataTask(ControllerUpdateType controllerUpdateType, Task task) {
		switch (controllerUpdateType) {
		case CREATE:
			task = TaskAccess.addTaskDataToDb(task);
			break;
		case MODIFIY:
			task = TaskAccess.updateTaskDataInDb(task);
			break;
		case DELETE:
			TaskAccess.removeTaskDataFromDb(task);
			break;
		default:
			break;
		}
		final Task finalTask = task;
		synchronized (taskListeners) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					for (RumUpdatableView rumUpdatableView : taskListeners) {
						rumUpdatableView.controllerUpdateNotify(controllerUpdateType, finalTask);
					}
				}
			});  
			thread.start();
		}
		return finalTask;
	}
	
	private Object changeDataUserFile(ControllerUpdateType controllerUpdateType, UserFile userFile) {
		switch (controllerUpdateType) {
		case CREATE:
			userFile = UserFileAccess.addUserFileDataToDb(userFile);
			break;
		case MODIFIY:
			userFile = UserFileAccess.updateUserFileDataInDb(userFile);
			break;
		case DELETE:
			UserFileAccess.removeUserFileDataFromDb(userFile);
			break;
		default:
			break;
		}
		final UserFile finalUserFile = userFile;
		synchronized (userFileListeners) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					for (RumUpdatableView rumUpdatableView : taskListeners) {
						rumUpdatableView.controllerUpdateNotify(controllerUpdateType, finalUserFile);
					}
				}
			});  
			thread.start();
		}
		return finalUserFile;
	}

	public void registerView(RumUpdatableView rumView, ControllerEntityType controllerEntityType) {
		switch (controllerEntityType) {
		case TASK:
			taskListeners.add(rumView);
			break;
		case PROJECT:
			projectListeners.add(rumView);
			break;
		case USER_FILE:
			userFileListeners.add(rumView);
			break;
		default:
			break;
		}

	}

	public void unregisterView(RumUpdatableView rumView, ControllerEntityType controllerEntityType) {
		switch (controllerEntityType) {
		case TASK:
			taskListeners.remove(rumView);
			break;
		case PROJECT:
			projectListeners.remove(rumView);
			break;
		case USER_FILE:
			userFileListeners.remove(rumView);
			break;
		default:
			break;
		}
	}
}
