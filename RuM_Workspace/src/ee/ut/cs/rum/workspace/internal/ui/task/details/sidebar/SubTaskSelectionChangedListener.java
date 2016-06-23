package ee.ut.cs.rum.workspace.internal.ui.task.details.sidebar;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import ee.ut.cs.rum.workspace.internal.ui.task.details.TaskDetailsComposite;

public class SubTaskSelectionChangedListener implements ISelectionChangedListener {
	private TaskDetailsComposite taskDetailsComposite;
	
	public SubTaskSelectionChangedListener(TaskDetailsComposite taskDetailsComposite) {
		this.taskDetailsComposite=taskDetailsComposite;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent arg0) {
		int selectionIndex=taskDetailsComposite.getDetailsSidebar().getSubTaskTableViewer().getTable().getSelectionIndex();
		if (selectionIndex == -1) {
			taskDetailsComposite.getTaskDetailsContainer().showGeneralInfo();
		} else {
			taskDetailsComposite.getTaskDetailsContainer().showSubTaskInfo(selectionIndex);			
		}
	}
}
