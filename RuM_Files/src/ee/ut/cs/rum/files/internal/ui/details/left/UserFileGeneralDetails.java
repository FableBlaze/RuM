package ee.ut.cs.rum.files.internal.ui.details.left;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class UserFileGeneralDetails extends Composite {

	private static final long serialVersionUID = 6962780544823550119L;

	public UserFileGeneralDetails(UserFileDetailsLeft userFileDetailsLeft) {
		super (userFileDetailsLeft, SWT.NONE);
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label l = new Label(this, SWT.NONE);
		l.setText("General details (TODO)");
	}

}
