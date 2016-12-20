package ee.ut.cs.rum.files.internal.ui.overview.filestable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.files.internal.Activator;

public class FileDownloadButton extends Button {
	private static final long serialVersionUID = 7432848176091752433L;
	
	private UserFile userFile;

	public FileDownloadButton(Composite parent, UserFile userFile) {
		super(parent, SWT.NONE);
		
		this.userFile=userFile;
		
		this.setText("Download");
		
		this.addSelectionListener(this.createSelectionListener());
	}
	
	private SelectionListener createSelectionListener() {
		return new SelectionListener() {
			private static final long serialVersionUID = -622701191074305980L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				UserFile userFile = ((FileDownloadButton) arg0.getSource()).getUserFile();
				Activator.getLogger().info("Download userFile: " + userFile.getId().toString());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		};
	}
	
	public UserFile getUserFile() {
		return userFile;
	}

}
