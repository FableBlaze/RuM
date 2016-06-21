package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;

public class NewTaskDetailsContainer extends Composite {
	private static final long serialVersionUID = -7982581022298012511L;
	
	private NewTaskComposite newTaskComposite;
	private NewTaskGeneralInfo newTaskGeneralInfo;
	
	//Lists only used for UI logic, subTasks read from the state of table when task started
	private List<SubTask> subTasks;
	private List<NewTaskSubTaskInfo> newTaskSubTaskInfoList;

	public NewTaskDetailsContainer(NewTaskComposite newTaskComposite, RumController rumController) {
		super(newTaskComposite, SWT.NONE);
		
		this.newTaskComposite=newTaskComposite;
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new StackLayout());
		
		newTaskGeneralInfo = new NewTaskGeneralInfo(this);
		
		this.subTasks = new ArrayList<SubTask>();
		this.newTaskSubTaskInfoList = new ArrayList<NewTaskSubTaskInfo>();
		
		((StackLayout)this.getLayout()).topControl = newTaskGeneralInfo;
		this.layout();
	}
	
	public void showGeneralInfo() {
		((StackLayout)this.getLayout()).topControl = newTaskGeneralInfo;
		this.layout();
		newTaskComposite.getNewTaskFooter().setRemoveSubTaskButtonVisible(false);
	}
	
	public void showSubTaskInfo(SubTask selectedSubTask) {
		int subTaskIndex = subTasks.indexOf(selectedSubTask);
		if (subTaskIndex==-1) {
			NewTaskSubTaskInfo newTaskSubTaskInfo = new NewTaskSubTaskInfo(this);
			((StackLayout)this.getLayout()).topControl = newTaskSubTaskInfo;
			subTasks.add(selectedSubTask);
			newTaskSubTaskInfoList.add(newTaskSubTaskInfo);
		} else {
			((StackLayout)this.getLayout()).topControl = newTaskSubTaskInfoList.get(subTaskIndex);
		}
		this.layout();
		newTaskComposite.getNewTaskFooter().setRemoveSubTaskButtonVisible(true);
	}
	
	public NewTaskGeneralInfo getNewTaskGeneralInfo() {
		return newTaskGeneralInfo;
	}
}
