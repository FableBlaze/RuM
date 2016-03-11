package ee.ut.cs.rum.administration.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.administration.internal.ui.SystemParametersTableViewer;

public class SystemAdministrationUI extends Composite {
	private static final long serialVersionUID = 3527292831706391069L;
	
	public SystemAdministrationUI(Composite parent) {
		super(parent, SWT.BORDER);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout());
		
		new SystemParametersTableViewer(this);
	}

}
