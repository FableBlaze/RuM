package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;

public class NewTaskDetailsContainer extends Composite {
	private static final long serialVersionUID = -7982581022298012511L;
	
	private RumController rumController;
	private NewTaskComposite newTaskComposite;
	private NewTaskGeneralInfo newTaskGeneralInfo;
	
	private List<NewTaskSubTaskInfo> newTaskSubTaskInfoList;

	public NewTaskDetailsContainer(NewTaskComposite newTaskComposite, RumController rumController) {
		super(newTaskComposite, SWT.NONE);
		
		this.rumController=rumController;
		this.newTaskComposite=newTaskComposite;
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new StackLayout());
		
		newTaskGeneralInfo = new NewTaskGeneralInfo(this);
		
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
	
	public NewTaskGeneralInfo getNewTaskGeneralInfo() {
		return newTaskGeneralInfo;
	}
	
	public List<NewTaskSubTaskInfo> getNewTaskSubTaskInfoList() {
		return newTaskSubTaskInfoList;
	}
	
	public void removeFromNewTaskSubTaskInfoList(int index) {
		newTaskSubTaskInfoList.remove(index);
	}
}
