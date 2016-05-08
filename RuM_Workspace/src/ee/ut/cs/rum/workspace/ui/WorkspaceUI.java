package ee.ut.cs.rum.workspace.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.workspace.internal.ui.WorkspaceHeader;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectsOverview;

public class WorkspaceUI extends Composite {
	private static final long serialVersionUID = 7689615370877170628L;

	private WorkspaceHeader workspacesHeader;
	private Composite workspaceContainer;
	private ProjectsOverview workspacesOverview;

	public WorkspaceUI(Composite parent) {
		super(parent, SWT.NONE);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout());
		
		//TODO: Fix the limitation that WorkspacesOverview has to be created before WorkspacesHeader
		workspacesOverview = new ProjectsOverview(this);
		
		workspacesHeader = new WorkspaceHeader(this);
		
		workspaceContainer = new Composite(this, SWT.NONE);
		workspaceContainer.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		workspaceContainer.setLayout(new StackLayout());

		workspacesOverview.setParent(workspaceContainer);
		
		((StackLayout)workspaceContainer.getLayout()).topControl = workspacesOverview;
		workspaceContainer.layout();
	}
	
	public WorkspaceHeader getWorkspaceHeader() {
		return workspacesHeader;
	}
	
	public Composite getWorkspaceContainer() {
		return workspaceContainer;
	}
	
	public ProjectsOverview getProjectsOverview() {
		return workspacesOverview;
	}
}
