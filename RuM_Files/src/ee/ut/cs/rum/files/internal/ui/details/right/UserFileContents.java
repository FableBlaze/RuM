package ee.ut.cs.rum.files.internal.ui.details.right;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class UserFileContents extends Composite {
	private static final long serialVersionUID = 7240993061617834628L;

	public UserFileContents(UserFileDetailsRight userFileDetailsRight) {
		super(userFileDetailsRight, SWT.NONE);
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label l = new Label(this, SWT.NONE);
		l.setText("Contents (TODO)");
	}

}
