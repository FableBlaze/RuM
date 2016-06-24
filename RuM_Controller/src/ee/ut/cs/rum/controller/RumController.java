package ee.ut.cs.rum.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ee.ut.cs.rum.controller.internal.Activator;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.domain.interfaces.RumUpdatableEntity;
import ee.ut.cs.rum.database.util.PluginAccess;
import ee.ut.cs.rum.database.util.ProjectAccess;
import ee.ut.cs.rum.database.util.SubTaskAccess;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.database.util.UserFileAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class RumController {

	private List<RumUpdatableView> projectListeners;
	private List<RumUpdatableView> taskListeners;
	private List<RumUpdatableView> userFileListeners;
	private List<RumUpdatableView> pluginListeners;
	private List<RumUpdatableView> subTaskListeners;

	public RumController() {
		projectListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
		taskListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
		userFileListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
		pluginListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
		subTaskListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
	}

	public Object changeData(ControllerUpdateType controllerUpdateType, ControllerEntityType controllerEntityType, Object updatedEntity) {
		Activator.getLogger().info("changeData - updateType: " + controllerUpdateType + ", entityType: " + controllerEntityType + ", entity: " + updatedEntity.toString());
		if (updatedEntity instanceof RumUpdatableEntity) {			
			updatedEntity = updateCreateModifyInfo((RumUpdatableEntity)updatedEntity, controllerUpdateType);
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
			case PLUGIN:
				if (updatedEntity instanceof Plugin) {
					Plugin plugin = (Plugin)updatedEntity;
					updatedEntity = changeDataPlugin(controllerUpdateType, plugin);				
				}
				break;
			case SUBTASK:
				if (updatedEntity instanceof SubTask) {
					SubTask subTask = (SubTask)updatedEntity;
					updatedEntity = changeDataSubTask(controllerUpdateType, subTask);				
				}
				break;
			default:
				break;
			}
		}
		return updatedEntity;
	}

	private Object updateCreateModifyInfo(RumUpdatableEntity updatedEntity, ControllerUpdateType controllerUpdateType) {
		Date date = new Date();
		if (controllerUpdateType==ControllerUpdateType.CREATE) {
			updatedEntity.setCreatedBy("TODO");
			updatedEntity.setCreatedAt(date);
		}
		updatedEntity.setLastModifiedBy("TODO");
		updatedEntity.setLastModifiedAt(date);
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
					for (RumUpdatableView rumUpdatableView : userFileListeners) {
						rumUpdatableView.controllerUpdateNotify(controllerUpdateType, finalUserFile);
					}
				}
			});  
			thread.start();
		}
		return finalUserFile;
	}
	
	private Object changeDataPlugin(ControllerUpdateType controllerUpdateType, Plugin plugin) {
		switch (controllerUpdateType) {
		case CREATE:
			plugin = PluginAccess.addPluginDataToDb(plugin);
			break;
		case MODIFIY:
			plugin = PluginAccess.updatePluginDataInDb(plugin);
			break;
		case DELETE:
			PluginAccess.removePluginDataFromDb(plugin);
			break;
		default:
			break;
		}
		final Plugin finalPlugin = plugin;
		synchronized (pluginListeners) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					for (RumUpdatableView rumUpdatableView : pluginListeners) {
						rumUpdatableView.controllerUpdateNotify(controllerUpdateType, finalPlugin);
					}
				}
			});  
			thread.start();
		}
		return finalPlugin;
	}
	
	private Object changeDataSubTask(ControllerUpdateType controllerUpdateType, SubTask subTask) {
		switch (controllerUpdateType) {
		case CREATE:
			subTask = SubTaskAccess.addSubTaskDataToDb(subTask);
			break;
		case MODIFIY:
			subTask = SubTaskAccess.updateSubTaskDataInDb(subTask);
			break;
		case DELETE:
			SubTaskAccess.removeSubTaskDataFromDb(subTask);
			break;
		default:
			break;
		}
		final SubTask finalSubTask = subTask;
		synchronized (subTaskListeners) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					for (RumUpdatableView rumUpdatableView : subTaskListeners) {
						rumUpdatableView.controllerUpdateNotify(controllerUpdateType, finalSubTask);
					}
				}
			});  
			thread.start();
		}
		return finalSubTask;
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
		case PLUGIN:
			pluginListeners.add(rumView);
			break;
		case SUBTASK:
			subTaskListeners.add(rumView);
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
		case PLUGIN:
			pluginListeners.remove(rumView);
			break;
		case SUBTASK:
			subTaskListeners.remove(rumView);
			break;
		default:
			break;
		}
	}
}
