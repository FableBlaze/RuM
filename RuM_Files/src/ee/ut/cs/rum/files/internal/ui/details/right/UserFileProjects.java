package ee.ut.cs.rum.files.internal.ui.details.right;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class UserFileProjects extends Composite {
	private static final long serialVersionUID = 7768740639368583284L;

	public UserFileProjects(UserFileDetailsRight userFileDetailsRight) {
		super(userFileDetailsRight, SWT.NONE);
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		Label l = new Label(this, SWT.NONE);
		l.setText("Projects (TODO)");
	}

}
