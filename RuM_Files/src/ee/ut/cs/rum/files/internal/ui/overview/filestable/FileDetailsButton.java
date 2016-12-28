package ee.ut.cs.rum.files.internal.ui.overview.filestable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.files.internal.ui.details.UserFileDetails;
import ee.ut.cs.rum.files.ui.FilesManagementUI;

public class FileDetailsButton extends Button {
	private static final long serialVersionUID = 1486627493897065620L;
	
	private RumController rumController;
	
	private UserFile userFile;
	private FilesManagementUI filesManagementUI;

	public FileDetailsButton(Composite parent, UserFile userFile, FilesManagementUI filesManagementUI, RumController rumController) {
		super(parent, SWT.NONE);
		
		this.userFile=userFile;
		this.filesManagementUI=filesManagementUI;
		
		this.setText("Details");
		
		this.addSelectionListener(this.createSelectionListener());
	}
	
	private SelectionListener createSelectionListener() {
		return new SelectionListener() {
			private static final long serialVersionUID = -970047420770233738L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				UserFile userFile = ((FileDetailsButton) arg0.getSource()).getUserFile();
				CTabItem cTabItem = null;
				
				//Checking if the tab is already open
				for (CTabItem c : filesManagementUI.getItems()) {
					if (c.getControl().getClass() == UserFileDetails.class) {
						if (((UserFileDetails)c.getControl()).getUserFile() == userFile) {
							cTabItem = c;
							filesManagementUI.setSelection(c);
						}
					}
				}
				
				if (cTabItem == null) {
					cTabItem = new CTabItem (filesManagementUI, SWT.CLOSE);
					cTabItem.setText (userFile.getOriginalFilename() + " - " + userFile.getId().toString());
					cTabItem.setControl(new UserFileDetails(filesManagementUI, userFile, rumController));
					filesManagementUI.setSelection(cTabItem);	
				}
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
