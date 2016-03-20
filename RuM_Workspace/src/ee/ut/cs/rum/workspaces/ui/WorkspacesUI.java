package ee.ut.cs.rum.workspaces.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.workspaces.internal.ui.WorkspacesHeader;
import ee.ut.cs.rum.workspaces.internal.ui.workspace.WorkspacesOverview;

public class WorkspacesUI extends Composite {
	private static final long serialVersionUID = 7689615370877170628L;

	private WorkspacesHeader workspacesHeader;
	private WorkspacesOverview workspacesOverview;

	public WorkspacesUI(Composite parent) {
		super(parent, SWT.BORDER);

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout());

		workspacesHeader = new WorkspacesHeader(this);

		workspacesOverview = new WorkspacesOverview(this);
	}
	
	public WorkspacesHeader getWorkspacesHeader() {
		return workspacesHeader;
	}
	
	public WorkspacesOverview getWorkspacesOverview() {
		return workspacesOverview;
	}
}
