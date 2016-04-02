package ee.ut.cs.rum.workspaces.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.workspaces.internal.ui.WorkspacesHeader;
import ee.ut.cs.rum.workspaces.internal.ui.workspace.WorkspacesOverview;

public class WorkspacesUI extends Composite {
	private static final long serialVersionUID = 7689615370877170628L;

	private WorkspacesHeader workspacesHeader;
	private Composite workspaceContainer;
	private WorkspacesOverview workspacesOverview;

	public WorkspacesUI(Composite parent) {
		super(parent, SWT.BORDER);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout());
		
		//TODO: Fix the limitation that WorkspacesOverview has to be created before WorkspacesHeader
		workspacesOverview = new WorkspacesOverview(this);
		
		workspacesHeader = new WorkspacesHeader(this);
		
		workspaceContainer = new Composite(this, SWT.NONE);
		workspaceContainer.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		workspaceContainer.setLayout(new StackLayout());

		workspacesOverview.setParent(workspaceContainer);
		
		((StackLayout)workspaceContainer.getLayout()).topControl = workspacesOverview;
		workspaceContainer.layout();
	}
	
	public WorkspacesHeader getWorkspacesHeader() {
		return workspacesHeader;
	}
	
	public Composite getWorkspaceContainer() {
		return workspaceContainer;
	}
	
	public WorkspacesOverview getWorkspacesOverview() {
		return workspacesOverview;
	}
}
