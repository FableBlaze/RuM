package ee.ut.cs.rum.files.internal.ui.overview.filestable;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.files.internal.ui.details.UserFileDetails;
import ee.ut.cs.rum.files.ui.FilesManagementUI;

public class FileRowDoubleClickListener implements IDoubleClickListener {
	
	private FilesManagementUI filesManagementUI;
	private RumController rumController;
	
	public FileRowDoubleClickListener(FilesManagementUI filesManagementUI, RumController rumController) {
		this.filesManagementUI=filesManagementUI;
	}
	
	@Override
	public void doubleClick(DoubleClickEvent event) {
		UserFile selectedUserFile = null;
		CTabItem cTabItem = null;
		
		if (event!=null) {
			//TODO: Open tab
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			selectedUserFile = (UserFile)selection.getFirstElement();
			
			//Checking if the tab is already open
			for (CTabItem c : filesManagementUI.getItems()) {
				if (c.getControl().getClass() == UserFileDetails.class) {
					if (((UserFileDetails)c.getControl()).getUserFile() == selectedUserFile) {
						cTabItem = c;
						filesManagementUI.setSelection(c);
					}
				}
			}
			
			if (cTabItem == null) {
				cTabItem = new CTabItem (filesManagementUI, SWT.CLOSE);
				cTabItem.setText (selectedUserFile.getOriginalFilename() + " - " + selectedUserFile.getId().toString());
				cTabItem.setControl(new UserFileDetails(filesManagementUI, selectedUserFile, rumController));
				filesManagementUI.setSelection(cTabItem);	
			}
			
		}
	}
}
