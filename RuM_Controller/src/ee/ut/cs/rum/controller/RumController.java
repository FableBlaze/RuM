package ee.ut.cs.rum.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.enums.ControllerListenerType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class RumController {

	private List<RumUpdatableView> taskListeners;

	public RumController() {
		taskListeners = Collections.synchronizedList(new ArrayList<RumUpdatableView>());
		// TODO Auto-generated constructor stub
	}
	
	public void changeDtata() {
		synchronized (taskListeners) {
		     for (RumUpdatableView rumUpdatableView : taskListeners) {
		    	 rumUpdatableView.controllerUpdateNotify(null, new Task());
			}
		  }
	}

	public void registerView(RumUpdatableView rumView, ControllerListenerType controllerListenerType) {
		switch (controllerListenerType) {
		case TASK:
			taskListeners.add(rumView);
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
		default:
			break;
		}
	}
}
