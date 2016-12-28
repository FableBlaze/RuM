package ee.ut.cs.rum.files.internal.ui.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.files.internal.ui.details.left.UserFileDetailsLeft;
import ee.ut.cs.rum.files.internal.ui.details.right.UserFileDetailsRight;
import ee.ut.cs.rum.files.ui.FilesManagementUI;

public class UserFileDetails extends Composite{
	private static final long serialVersionUID = 2568497081797282411L;
	
	private RumController rumController;
	
	private UserFile userFile;

	public UserFileDetails(FilesManagementUI filesManagementUI, UserFile userFile, RumController rumController2) {
		super(filesManagementUI, SWT.NONE);
		
		this.userFile=userFile;
		
		this.setLayout(new GridLayout(2, false));
		
		new UserFileDetailsLeft(this);
		new UserFileDetailsRight(this);
		
		UserFileFooter userFileFooter = new UserFileFooter(this);
		((GridData) userFileFooter.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
	}

	public UserFile getUserFile() {
		return userFile;
	}

}
