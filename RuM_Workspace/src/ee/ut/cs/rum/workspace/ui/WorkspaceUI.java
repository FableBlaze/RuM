package ee.ut.cs.rum.workspace.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.WorkspaceHeader;
import ee.ut.cs.rum.workspace.internal.ui.overview.WorkspaceOverview;

public class WorkspaceUI extends Composite {
	private static final long serialVersionUID = 7689615370877170628L;

	private WorkspaceHeader workspaceHeader;
	private Composite workspaceContainer;
	private WorkspaceOverview workspaceOverview;

	public WorkspaceUI(Composite parent, RumController rumController) {
		super(parent, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout());
		
		workspaceHeader = new WorkspaceHeader(this, rumController);
		
		workspaceContainer = new Composite(this, SWT.NONE);
		workspaceContainer.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		workspaceContainer.setLayout(new StackLayout());

		workspaceOverview = new WorkspaceOverview(workspaceContainer, this, rumController);
		
		((StackLayout)workspaceContainer.getLayout()).topControl = workspaceOverview;
		workspaceContainer.layout();
	}
	
	public WorkspaceHeader getWorkspaceHeader() {
		return workspaceHeader;
	}
	
	public Composite getWorkspaceContainer() {
		return workspaceContainer;
	}
	
	public WorkspaceOverview getWorkspaceOverview() {
		return workspaceOverview;
	}
}
