package ee.ut.cs.rum.workspaces.internal.ui.workspace;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;

import ee.ut.cs.rum.database.domain.Workspace;
import ee.ut.cs.rum.database.util.WorkspaceAccess;
import ee.ut.cs.rum.workspaces.internal.ui.WorkspacesHeader;

public class WorkspaceSelectorCombo extends Combo {
	private static final long serialVersionUID = -1671918025859199853L;

	public WorkspaceSelectorCombo(WorkspacesHeader workspacesHeader) {
		super(workspacesHeader, SWT.READ_ONLY);
		
		this.add("Overview");
		this.setVisibleItemCount(10);
		this.select(0);
		updateWorkspaceSelector();
	}
	
	private void updateWorkspaceSelector() {
		if (this.getItemCount()>1) {
			this.remove(1, this.getItemCount()-1);
		}
		for (Workspace workspace : WorkspaceAccess.getWorkspacesDataFromDb()) {
			this.add(workspace.getName());
		}
	}

	public void updateWorkspaceSelector(List<Workspace> workspaces) {
		if (this.getItemCount()>1) {
			this.remove(1, this.getItemCount()-1);
		}
		for (Workspace workspace : workspaces) {
			this.add(workspace.getName());
		}
	}

}
