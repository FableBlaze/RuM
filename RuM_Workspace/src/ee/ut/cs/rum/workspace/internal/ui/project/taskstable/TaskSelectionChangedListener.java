package ee.ut.cs.rum.workspace.internal.ui.project.taskstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectDetailsContainer;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectOverview;

public class TaskSelectionChangedListener implements ISelectionChangedListener {

	private ProjectOverview projectOverview;

	public TaskSelectionChangedListener(ProjectOverview projectOverview) {
		this.projectOverview=projectOverview;
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		Task selectedTask=null;

		if (event!=null) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			selectedTask = (Task) selection.getFirstElement();			
		}

		ProjectDetailsContainer projectDetailsContainer = projectOverview.getProjectDetailsContainer(); 
		projectDetailsContainer.showDetailsOf(selectedTask);
	}

}
