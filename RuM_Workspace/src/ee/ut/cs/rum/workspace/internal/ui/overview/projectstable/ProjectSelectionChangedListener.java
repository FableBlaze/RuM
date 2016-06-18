package ee.ut.cs.rum.workspace.internal.ui.overview.projectstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.workspace.internal.ui.overview.WorkspaceDetailsContainer;
import ee.ut.cs.rum.workspace.internal.ui.overview.WorkspaceOverview;

public class ProjectSelectionChangedListener implements ISelectionChangedListener {

	private WorkspaceOverview workspaceOverview;
	
	public ProjectSelectionChangedListener(WorkspaceOverview workspaceOverview) {
		this.workspaceOverview = workspaceOverview;
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		Project selectedProject=null;
		
		if (event!=null) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			selectedProject = (Project) selection.getFirstElement();			
		}
		
		WorkspaceDetailsContainer workspaceDetailsContainer = workspaceOverview.getWorkspaceDetailsContainer(); 
		workspaceDetailsContainer.showDetailsOf(selectedProject);
		
	}
}
