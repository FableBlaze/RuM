package ee.ut.cs.rum.workspaces.internal.ui.workspace;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.workspaces.internal.ui.dialog.NewWorkspaceDialog;
import ee.ut.cs.rum.workspaces.ui.WorkspacesUI;

public class WorkspacesOverview extends Composite {
	private static final long serialVersionUID = -2991325315513334549L;
	
	private WorkspacesTableViewer workspacesTableViewer;
	public WorkspacesOverview(WorkspacesUI workspacesUI) {
		super(workspacesUI, SWT.NONE);

		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.workspacesTableViewer = new WorkspacesTableViewer(this);
		
		Button addWorkspaceDialogueButton = new Button(this, SWT.PUSH);
		addWorkspaceDialogueButton.setText("Add Workspace");
		addWorkspaceDialogueButton.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false));
		
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
}
