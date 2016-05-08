package ee.ut.cs.rum.workspace.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.workspace.internal.ui.workspace.WorkspaceSelectorCombo;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

public class WorkspaceHeader extends Composite {
	private static final long serialVersionUID = -6786154655313853465L;

	private Label workspaceTitle;
	private WorkspaceSelectorCombo workspaceSelectorCombo;

	public WorkspaceHeader(WorkspaceUI workspacesUI) {
		super(workspacesUI, SWT.NONE);

		this.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		this.setLayout(new GridLayout(3, false));

		workspaceTitle = new Label(this, SWT.NONE);
		workspaceTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

		Label workspaceSelectorLabel = new Label(this, SWT.NONE);
		workspaceSelectorLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		workspaceSelectorLabel.setText("Select workspace: ");

		this.workspaceSelectorCombo = new WorkspaceSelectorCombo(this, workspacesUI);
		
		workspaceTitle.setText("Workspaces overview");
	}

	public void setWorkspaceTitle(String titleString) {
		workspaceTitle.setText(titleString);
	}
	
	public WorkspaceSelectorCombo getWorkspaceSelectorCombo() {
		return workspaceSelectorCombo;
	}
}
