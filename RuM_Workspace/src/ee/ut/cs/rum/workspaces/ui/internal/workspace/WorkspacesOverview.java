package ee.ut.cs.rum.workspaces.ui.internal.workspace;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class WorkspacesOverview extends Composite {
	private static final long serialVersionUID = -2991325315513334549L;
	
	public WorkspacesOverview(Composite parent) {
		super(parent, SWT.NONE);

		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		new WorkspacesTableViewer(this);
	}

}
