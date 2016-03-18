package ee.ut.cs.rum.workspace.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import ee.ut.cs.rum.workspace.ui.internal.newtask.NewTaskComposite;

public class WorkspaceUI extends Composite {
	private static final long serialVersionUID = 7689615370877170628L;

	public WorkspaceUI(Composite parent) {
		super(parent, SWT.BORDER);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout());
		
		new NewTaskComposite(this);
	}

}
