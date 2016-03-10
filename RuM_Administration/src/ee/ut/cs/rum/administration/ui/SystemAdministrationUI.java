package ee.ut.cs.rum.administration.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class SystemAdministrationUI extends Composite {
	private static final long serialVersionUID = 7689615370877170628L;

	public SystemAdministrationUI(Composite parent) {
		super(parent, SWT.BORDER);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2, false));
		
		Label l = new Label(this, SWT.NONE);
		l.setText("System administration contents (TODO)");
	}

}
