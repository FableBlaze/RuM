package ee.ut.cs.rum.workspace.internal.ui.workspace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.util.ProjectAccess;
import ee.ut.cs.rum.workspace.internal.Activator;
import ee.ut.cs.rum.workspace.internal.ui.WorkspaceHeader;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

public class WorkspaceSelectorCombo extends Combo {
	private static final long serialVersionUID = -1671918025859199853L;
	
	//TODO: WorkspacesOverview should not be handled trough workspaceDetails list
	private List<Project> workspaces;
	private List<Composite> workspaceDetails;
	private WorkspaceUI workspacesUI;
	private WorkspaceHeader workspacesHeader;

	public WorkspaceSelectorCombo(WorkspaceHeader workspacesHeader, WorkspaceUI workspacesUI) {
		super(workspacesHeader, SWT.READ_ONLY);
		this.workspacesUI=workspacesUI;
		this.workspacesHeader=workspacesHeader;

		this.add("Overview");
		this.setVisibleItemCount(10);
		this.select(0);
		updateWorkspaceSelector();

		this.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -1752969541573951231L;

			public void handleEvent(Event e) {
				updateSelectedWorkspaceDetails();
			}
		});
	}
	
	private void updateWorkspaceSelector() {
		this.workspaces = new ArrayList<Project>();
		this.workspaces.add(null);
		this.workspaces.addAll(ProjectAccess.getProjectssDataFromDb());
		createWorkspaceDetailsList(workspaces.size());

		for (Project workspace : workspaces) {
			if (workspace!=null) {
				this.add(workspace.getName());
			}
		}
	}

	public void updateSelectedWorkspaceDetails() {
		int selectedIndex = WorkspaceSelectorCombo.this.getSelectionIndex();
		StackLayout workspaceContainerLayout = (StackLayout)workspacesUI.getWorkspaceContainer().getLayout();
		Composite selectedWorkspaceDetails = workspaceDetails.get(selectedIndex);
		
		if (selectedWorkspaceDetails==null) {
			selectedWorkspaceDetails = new WorkspaceTabFolder(workspacesUI.getWorkspaceContainer(), workspaces.get(selectedIndex));
			workspaceDetails.add(selectedIndex, selectedWorkspaceDetails);
		}
		
		workspaceContainerLayout.topControl=selectedWorkspaceDetails;
		workspacesUI.getWorkspaceContainer().layout();
		
		if (workspaces.get(selectedIndex)!=null) {
			workspacesHeader.setWorkspaceTitle(workspaces.get(selectedIndex).getName());
			Activator.getLogger().info("Opened workspace: " + workspaces.get(selectedIndex).toString());
		} else {
			workspacesHeader.setWorkspaceTitle("Workspaces overview");
			Activator.getLogger().info("Opened workspaces overview");
		}
	}
	
	private void createWorkspaceDetailsList(int size) {
		this.workspaceDetails = new ArrayList<Composite>();
		workspaceDetails.add(workspacesUI.getWorkspacesOverview());
		for (int i = 1; i < size; i++) {
			workspaceDetails.add(null);
		}
	}

	public void updateWorkspaceSelector(List<Project> workspaces) {
		this.workspaces = new ArrayList<Project>();
		this.workspaces.add(null);
		this.workspaces.addAll(workspaces);
		//TODO: Update indexes instead of creating a new list
		createWorkspaceDetailsList(this.workspaces.size());
		if (this.getItemCount()>1) {
			this.remove(1, this.getItemCount()-1);
		}
		for (Project workspace : workspaces) {
			if (workspace!=null) {
				this.add(workspace.getName());
			}
		}
	}

}
