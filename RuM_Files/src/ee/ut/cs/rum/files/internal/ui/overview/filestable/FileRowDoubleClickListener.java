package ee.ut.cs.rum.files.internal.ui.overview.filestable;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.custom.CTabItem;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.files.internal.Activator;
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
			Activator.getLogger().info("File doubleclick: "+selectedUserFile.getOriginalFilename());
		}
	}
}
