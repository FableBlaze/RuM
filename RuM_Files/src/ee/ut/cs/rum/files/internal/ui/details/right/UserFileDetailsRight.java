package ee.ut.cs.rum.files.internal.ui.details.right;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.files.internal.ui.details.UserFileDetails;

public class UserFileDetailsRight extends Composite {
	private static final long serialVersionUID = -151912746099723372L;

	public UserFileDetailsRight(UserFileDetails userFileDetails) {
		super(userFileDetails, SWT.NONE);
		
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		new UserFileContents(this);
		new UserFileProjects(this);
	}

}
