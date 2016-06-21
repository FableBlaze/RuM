package ee.ut.cs.rum.workspace.internal.ui.task.newtask.sidebar;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskComposite;

public class SubTaskSelectionChangedListener implements ISelectionChangedListener {
	private NewTaskComposite newTaskComposite;
	
	public SubTaskSelectionChangedListener(NewTaskComposite newTaskComposite) {
		this.newTaskComposite=newTaskComposite;
	}
	
	public void selectionChanged(final SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		SubTask selectedSubTask = (SubTask) selection.getFirstElement();
		newTaskComposite.getNewTaskDetailsContainer().showSubTaskInfo(selectedSubTask);
	}
}
