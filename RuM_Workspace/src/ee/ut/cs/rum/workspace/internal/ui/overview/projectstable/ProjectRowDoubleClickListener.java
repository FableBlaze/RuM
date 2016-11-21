package ee.ut.cs.rum.workspace.internal.ui.overview.projectstable;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;

import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.workspace.internal.ui.overview.WorkspaceOverview;

public class ProjectRowDoubleClickListener implements IDoubleClickListener {
	
	private WorkspaceOverview workspaceOverview;
	
	public ProjectRowDoubleClickListener(WorkspaceOverview workspaceOverview) {
		this.workspaceOverview = workspaceOverview;
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		Project selectedProject=null;
		
		if (event!=null) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			selectedProject = (Project) selection.getFirstElement();			
			workspaceOverview.getWorkspaceUI().getWorkspaceHeader().getProjectSelectorCombo().selectProject(selectedProject);
		}
		
	}

}
