package ee.ut.cs.rum.workspace.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class WorkspaceProjectDetails extends Composite {
	private static final long serialVersionUID = -5990558506997308715L;

	WorkspaceProjectDetails(Composite parent) {
		super(parent, SWT.NONE);
		
		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new StackLayout());
		
		Label l = new Label(this, SWT.BORDER);
		l.setText("WorkspaceProjectDetails");
	}

}
