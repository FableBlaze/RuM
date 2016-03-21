package ee.ut.cs.rum.workspaces.internal.ui.workspace;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.Workspace;

public class WorkspaceDetails extends Composite {
	private static final long serialVersionUID = 3261215361750051333L;

	WorkspaceDetails(Composite workspaceContainer, Workspace workspace) {
		super(workspaceContainer, SWT.BORDER);
		
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label l = new Label(this, SWT.BORDER);
		l.setText(workspace.getName());
		l = new Label(this, SWT.BORDER);
		l.setText(workspace.getDescription());
	}

}
