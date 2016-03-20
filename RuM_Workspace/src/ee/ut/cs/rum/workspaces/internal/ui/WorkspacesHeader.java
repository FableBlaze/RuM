package ee.ut.cs.rum.workspaces.internal.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.Workspace;
import ee.ut.cs.rum.database.util.WorkspaceAccess;
import ee.ut.cs.rum.workspaces.ui.WorkspacesUI;

public class WorkspacesHeader extends Composite {
	private static final long serialVersionUID = -6786154655313853465L;

	private Label workspaceTitle;
	private Combo workspaceSelector;

	public WorkspacesHeader(WorkspacesUI workspacesUI) {
		super(workspacesUI, SWT.NONE);

		this.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		this.setLayout(new GridLayout(3, false));

		workspaceTitle = new Label(this, SWT.NONE);
		workspaceTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

		Label workspaceSelectorLabel = new Label(this, SWT.NONE);
		workspaceSelectorLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		workspaceSelectorLabel.setText("Select workspace: ");

		workspaceSelector = new Combo(this, SWT.READ_ONLY);
		workspaceSelector.add("Overview");
		workspaceSelector.setVisibleItemCount(10);
		updateWorkspaceSelector();
		
		workspaceSelector.select(0);
		workspaceTitle.setText("Workspaces overview");
	}

	public void setWorkspaceTitle(String titleString) {
		workspaceTitle.setText(titleString);
	}
	
	private void updateWorkspaceSelector() {
		if (workspaceSelector.getItemCount()>1) {
			workspaceSelector.remove(1, workspaceSelector.getItemCount()-1);
		}
		for (Workspace workspace : WorkspaceAccess.getWorkspacesDataFromDb()) {
			workspaceSelector.add(workspace.getName());
		}
	}

	public void updateWorkspaceSelector(List<Workspace> workspaces) {
		if (workspaceSelector.getItemCount()>1) {
			workspaceSelector.remove(1, workspaceSelector.getItemCount()-1);
		}
		for (Workspace workspace : workspaces) {
			workspaceSelector.add(workspace.getName());
		}
	}
}
