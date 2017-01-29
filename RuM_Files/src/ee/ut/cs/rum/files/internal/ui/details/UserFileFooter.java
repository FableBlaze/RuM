package ee.ut.cs.rum.files.internal.ui.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.files.internal.download.FileDownloadButton;

public class UserFileFooter extends Composite {
	private static final long serialVersionUID = 5327248514196828368L;

	UserFileFooter(UserFileDetails userFileDetails, UserFile userFile) {
		super(userFileDetails, SWT.NONE);
		
		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label locationLabel = new Label(this, SWT.NONE);
		locationLabel.setText("File location:");
		locationLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		
		Label locationText = new Label(this, SWT.NONE);
		locationText.setText(userFile.getFileLocation());
		locationText.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		
		FileDownloadButton fileDownloadButton = new FileDownloadButton(this, userFile);
		fileDownloadButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
	}

}
