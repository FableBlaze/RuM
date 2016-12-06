package ee.ut.cs.rum.files.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;

public class FilesManagementUI extends Composite {
	private static final long serialVersionUID = 2757777177503139030L;

	public FilesManagementUI(Composite parent, RumController rumController) {
		super(parent, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout());
		
		Label l = new Label(this, SWT.NONE);
		l.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		l.setText("TODO");
	}
}
