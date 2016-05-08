package ee.ut.cs.rum.workspace.internal.ui.workspace;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.database.domain.Project;

public class WorkspaceTabFolder extends CTabFolder {
	private static final long serialVersionUID = 3261215361750051333L;
	
	private CTabItem workspaceDetailsTab;
	private WorkspaceDetailsTabContents workspaceDetailsTabContents;
	private Project workspace;
	
	public WorkspaceTabFolder(Composite workspaceContainer, Project workspace) {
		super(workspaceContainer, SWT.BORDER);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.workspace=workspace;
		
		this.workspaceDetailsTab = new CTabItem (this, SWT.NONE);
		workspaceDetailsTab.setText ("Workspace details");
		
		this.workspaceDetailsTabContents = new WorkspaceDetailsTabContents(this, workspaceContainer, workspace);
		workspaceDetailsTab.setControl (workspaceDetailsTabContents);
		
		this.setSelection(0);
	}
	
	public Project getWorkspace() {
		return workspace;
	}
	
	public WorkspaceDetailsTabContents getWorkspaceDetailsTabContents() {
		return workspaceDetailsTabContents;
	}
}
