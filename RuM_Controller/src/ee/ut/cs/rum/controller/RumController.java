package ee.ut.cs.rum.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ee.ut.cs.rum.controller.internal.Activator;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.enums.ControllerListenerType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class RumController {

	private List<RumUpdatableView> projectListeners;
	private List<RumUpdatableView> taskListeners;

	public RumController() {
		taskListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
		projectListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
	}
	
	
	@Deprecated
	public void changeDtata() {
		synchronized (projectListeners) {
			Thread thread = new Thread(new Runnable() {
			     public void run() {
			    	 for (RumUpdatableView rumUpdatableView : projectListeners) {
			    		 Activator.getLogger().info("Notify");
			    		 Project project = new Project();
			    		 project.setName(new Date().toString());
			    		 rumUpdatableView.controllerUpdateNotify(ControllerUpdateType.CREATE, project);
			    		 Activator.getLogger().info("Notify done");
			    	 }
			     }
			});  
			thread.start();
		}
	}
	
	
	

	public void registerView(RumUpdatableView rumView, ControllerListenerType controllerListenerType) {
		switch (controllerListenerType) {
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

	public void unregisterView(RumUpdatableView rumView, ControllerListenerType controllerListenerType) {
		switch (controllerListenerType) {
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
