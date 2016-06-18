package ee.ut.cs.rum.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ee.ut.cs.rum.controller.internal.Activator;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.util.ProjectAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class RumController {

	private List<RumUpdatableView> projectListeners;
	private List<RumUpdatableView> taskListeners;

	public RumController() {
		taskListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
		projectListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
	}

	public void changeData(ControllerUpdateType controllerUpdateType, ControllerEntityType controllerEntityType, Object updatedEntity) {
		Activator.getLogger().info("changeData - updateType: " + controllerUpdateType + ", entityType: " + controllerEntityType + ", entity: " + updatedEntity.toString());
		switch (controllerEntityType) {
		case PROJECT:
			if (updatedEntity instanceof Project) {
				Project project = (Project)updatedEntity;
				changeDataProject(controllerUpdateType, project);				
			}
			break;
		default:
			break;
		}
	}
	
	private void changeDataProject(ControllerUpdateType controllerUpdateType, Project project) {
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
		synchronized (projectListeners) {
			final Project finalProject = project;
			Thread thread = new Thread(new Runnable() {
				public void run() {
					for (RumUpdatableView rumUpdatableView : projectListeners) {
						rumUpdatableView.controllerUpdateNotify(controllerUpdateType, finalProject);
					}
				}
			});  
			thread.start();
		}
	}

	public void registerView(RumUpdatableView rumView, ControllerEntityType controllerEntityType) {
		switch (controllerEntityType) {
		case TASK:
			taskListeners.add(rumView);
			break;
		case PROJECT:
			projectListeners.add(rumView);
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
		default:
			break;
		}
	}
}
