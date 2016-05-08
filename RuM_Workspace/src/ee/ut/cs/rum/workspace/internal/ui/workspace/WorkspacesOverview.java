package ee.ut.cs.rum.workspace.internal.ui.workspace;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.workspace.internal.ui.workspace.dialog.NewWorkspaceDialog;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

public class WorkspacesOverview extends Composite {
	private static final long serialVersionUID = -2991325315513334549L;
	
	private WorkspaceUI workspacesUI;
	private WorkspacesTableViewer workspacesTableViewer;
	
	public WorkspacesOverview(WorkspaceUI workspacesUI) {
		super(workspacesUI, SWT.NONE);
		this.workspacesUI=workspacesUI;

		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.workspacesTableViewer = new WorkspacesTableViewer(this);
		
		Button addWorkspaceDialogueButton = new Button(this, SWT.PUSH);
		addWorkspaceDialogueButton.setText("Add Workspace");
		addWorkspaceDialogueButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false));
		
		addWorkspaceDialogueButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 5383804225331390829L;

			public void handleEvent(Event arg0) {
				NewWorkspaceDialog newWorkspaceDialog = new NewWorkspaceDialog(Display.getCurrent().getActiveShell(), workspacesUI);
				newWorkspaceDialog.open();
			}
		});
	}
	
	public WorkspacesTableViewer getWorkspacesTableViewer() {
		return workspacesTableViewer;
	}
	
	public WorkspaceUI getWorkspacesUI() {
		return workspacesUI;
	}
}
