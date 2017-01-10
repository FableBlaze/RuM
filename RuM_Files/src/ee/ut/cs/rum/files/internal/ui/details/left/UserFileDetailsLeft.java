package ee.ut.cs.rum.files.internal.ui.details.left;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.files.internal.ui.details.UserFileDetails;

public class UserFileDetailsLeft extends Composite {
	private static final long serialVersionUID = 1818511302735077156L;
	
	private UserFileDetails userFileDetails;

	public UserFileDetailsLeft(UserFileDetails userFileDetails) {
		super(userFileDetails, SWT.NONE);
		
		this.userFileDetails=userFileDetails;
		
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		
		new UserFileGeneralDetails(this);
		new UserFileSettings(this);
	}
	
	public UserFileDetails getUserFileDetails() {
		return userFileDetails;
	}

}
