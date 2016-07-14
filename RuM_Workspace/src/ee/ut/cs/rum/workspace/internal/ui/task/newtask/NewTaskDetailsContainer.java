package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.util.UserFileAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.workspace.internal.Activator;

public class NewTaskDetailsContainer extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = -7982581022298012511L;
	
	private RumController rumController;
		
	private NewTaskComposite newTaskComposite;
	private NewTaskGeneralInfo newTaskGeneralInfo;
	
	private List<UserFile> userFiles;
	private List<NewTaskSubTaskInfo> newTaskSubTaskInfoList;

	public NewTaskDetailsContainer(NewTaskComposite newTaskComposite, RumController rumController) {
		super(newTaskComposite, SWT.NONE);
		
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.USER_FILE);
		
		this.newTaskComposite=newTaskComposite;
		this.userFiles = UserFileAccess.getProjectUserFilesDataFromDb(newTaskComposite.getProjectTabFolder().getProject().getId());
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new StackLayout());
		
		this.newTaskGeneralInfo = new NewTaskGeneralInfo(this);
		
		this.newTaskSubTaskInfoList = new ArrayList<NewTaskSubTaskInfo>();
		
		((StackLayout)this.getLayout()).topControl = newTaskGeneralInfo;
		this.layout();
	}
	
	public void showGeneralInfo() {
		((StackLayout)this.getLayout()).topControl = newTaskGeneralInfo;
		this.layout();
		newTaskComposite.getNewTaskFooter().setRemoveSubTaskButtonVisible(false);
	}
	
	public void showSubTaskInfo(int subTaskIndex) {
		if (subTaskIndex < newTaskSubTaskInfoList.size()) {
			((StackLayout)this.getLayout()).topControl = newTaskSubTaskInfoList.get(subTaskIndex);
		} else {
			NewTaskSubTaskInfo newTaskSubTaskInfo = new NewTaskSubTaskInfo(this, rumController);
			((StackLayout)this.getLayout()).topControl = newTaskSubTaskInfo;
			newTaskSubTaskInfoList.add(newTaskSubTaskInfo);
		}
		this.layout();
		newTaskComposite.getNewTaskFooter().setRemoveSubTaskButtonVisible(true);
	}
	
	public NewTaskComposite getNewTaskComposite() {
		return newTaskComposite;
	}
	
	public NewTaskGeneralInfo getNewTaskGeneralInfo() {
		return newTaskGeneralInfo;
	}
	
	public List<NewTaskSubTaskInfo> getNewTaskSubTaskInfoList() {
		return newTaskSubTaskInfoList;
	}
	
	public List<UserFile> getUserFiles() {
		return userFiles;
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		//Handle updates
		Activator.getLogger().info("Update recived (TODO)");
	}
	
	@Override
	public void dispose() {
		rumController.unregisterView(this, ControllerEntityType.PROJECT);
		super.dispose();
	}

}
