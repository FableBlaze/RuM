package ee.ut.cs.rum.files.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.util.UserFileAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class FilesOverview extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = -2947707344470112645L;
	
	private Display display;
	private RumController rumController;
	
	private Label totalUserFiles;

	public FilesOverview(OverviewTabContents overviewTabContents, RumController rumController) {
		super(overviewTabContents, SWT.NONE);
		
		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.USER_FILE);
		
		this.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
		this.setLayout(new GridLayout(2, false));
		((GridData) this.getLayoutData()).widthHint=200;
		
		createContents();
	}
	
	private void createContents() {
		Label label = new Label(this, SWT.NONE);
		label.setText("Total plugins:");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));
		totalUserFiles = new Label(this, SWT.NONE);
		totalUserFiles.setText(Integer.toString(UserFileAccess.getUserFilesDataFromDb().size()));
		totalUserFiles.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));
	}
	
	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof UserFile) {
			display.asyncExec(new Runnable() {
				public void run() {
					int totalUserFilesCount = Integer.parseInt(totalUserFiles.getText());
					switch (updateType) {
					case CREATE:
						totalUserFilesCount+=1;
						break;
					case DELETE:
						totalUserFilesCount-=1;
						break;
					default:
						break;
					}
					totalUserFiles.setText(Integer.toString(totalUserFilesCount));							
				}
			});
		}
	}
	
	@Override
	public void dispose() {
		rumController.unregisterView(this, ControllerEntityType.USER_FILE);
		super.dispose();
	}
}
