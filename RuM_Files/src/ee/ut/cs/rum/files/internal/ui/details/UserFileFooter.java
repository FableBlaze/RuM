package ee.ut.cs.rum.files.internal.ui.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class UserFileFooter extends Composite {
	private static final long serialVersionUID = 5327248514196828368L;

	UserFileFooter(UserFileDetails userFileDetails) {
		super(userFileDetails, SWT.NONE);
		
		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label l = new Label(this, SWT.NONE);
		l.setText("Footer (TODO)");
	}

}
