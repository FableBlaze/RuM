package ee.ut.cs.rum.workspace.internal.ui.task.newtask.sidebar;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskComposite;

public class SubTaskSelectionChangedListener implements ISelectionChangedListener {
	private NewTaskComposite newTaskComposite;
	
	public SubTaskSelectionChangedListener(NewTaskComposite newTaskComposite) {
		this.newTaskComposite=newTaskComposite;
	}
	
	public void selectionChanged(final SelectionChangedEvent event) {
		int selectionIndex = newTaskComposite.getDetailsSideBar().getSubTaskTableViewer().getTable().getSelectionIndex();
		if (selectionIndex == -1) {
			newTaskComposite.getNewTaskDetailsContainer().showGeneralInfo();
		} else {
			newTaskComposite.getNewTaskDetailsContainer().showSubTaskInfo(selectionIndex);			
		}
	}
}
