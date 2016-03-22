package ee.ut.cs.rum.workspaces.internal.ui.workspace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.database.domain.Workspace;
import ee.ut.cs.rum.database.util.WorkspaceAccess;
import ee.ut.cs.rum.workspaces.internal.Activator;
import ee.ut.cs.rum.workspaces.internal.ui.WorkspacesHeader;
import ee.ut.cs.rum.workspaces.ui.WorkspacesUI;

public class WorkspaceSelectorCombo extends Combo {
	private static final long serialVersionUID = -1671918025859199853L;
	
	//TODO: WorkspacesOverview should not be handled trough workspaceDetails list
	private List<Workspace> workspaces;
	private List<Composite> workspaceDetails;
	private WorkspacesUI workspacesUI;

	public WorkspaceSelectorCombo(WorkspacesHeader workspacesHeader, WorkspacesUI workspacesUI) {
		super(workspacesHeader, SWT.READ_ONLY);

		this.workspacesUI=workspacesUI;

		this.add("Overview");
		this.setVisibleItemCount(10);
		this.select(0);
		updateWorkspaceSelector();

		this.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5264275242878279673L;

			public void widgetSelected( SelectionEvent event ) {
				int selectedIndex = WorkspaceSelectorCombo.this.getSelectionIndex();
				StackLayout workspaceContainerLayout = (StackLayout)workspacesUI.getWorkspaceContainer().getLayout();
				Composite selectedWorkspaceDetails = workspaceDetails.get(selectedIndex);
				
				if (selectedWorkspaceDetails==null) {
					selectedWorkspaceDetails = new WorkspaceDetails(workspacesUI.getWorkspaceContainer(), workspaces.get(selectedIndex));
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
		});
	}

	private void updateWorkspaceSelector() {
		this.workspaces = new ArrayList<Workspace>();
		this.workspaces.add(null);
		this.workspaces.addAll(WorkspaceAccess.getWorkspacesDataFromDb());
		createWorkspaceDetailsList(workspaces.size());

		for (Workspace workspace : workspaces) {
			if (workspace!=null) {
				this.add(workspace.getName());
			}
		}
	}

	private void createWorkspaceDetailsList(int size) {
		this.workspaceDetails = new ArrayList<Composite>();
		workspaceDetails.add(workspacesUI.getWorkspacesOverview());
		for (int i = 1; i < size; i++) {
			workspaceDetails.add(null);
		}
	}

	public void updateWorkspaceSelector(List<Workspace> workspaces) {
		this.workspaces = new ArrayList<Workspace>();
		this.workspaces.add(null);
		this.workspaces.addAll(workspaces);
		//TODO: Update indexes instead of creating a new list
		createWorkspaceDetailsList(workspaces.size());
		if (this.getItemCount()>1) {
			this.remove(1, this.getItemCount()-1);
		}
		for (Workspace workspace : workspaces) {
			if (workspace!=null) {
				this.add(workspace.getName());
			}
		}
	}

}