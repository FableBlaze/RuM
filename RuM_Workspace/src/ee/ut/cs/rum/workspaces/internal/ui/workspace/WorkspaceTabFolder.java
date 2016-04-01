package ee.ut.cs.rum.workspaces.internal.ui.workspace;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.database.domain.Workspace;

public class WorkspaceTabFolder extends CTabFolder {
	private static final long serialVersionUID = 3261215361750051333L;
	
	private CTabItem workspaceDetailsTab;
	private WorkspaceDetailsTabContents workspaceDetailsTabContents;
	private Workspace workspace;
	
	public WorkspaceTabFolder(Composite workspaceContainer, Workspace workspace) {
		super(workspaceContainer, SWT.NONE);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.workspace=workspace;
		
		this.workspaceDetailsTab = new CTabItem (this, SWT.NONE);
		workspaceDetailsTab.setText ("Workspace details");
		
		this.workspaceDetailsTabContents = new WorkspaceDetailsTabContents(this, workspaceContainer, workspace);
		workspaceDetailsTab.setControl (workspaceDetailsTabContents);
		
		this.setSelection(0);
	}
	
	public Workspace getWorkspace() {
		return workspace;
	}
}
