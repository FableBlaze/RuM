package ee.ut.cs.rum.workspace.internal.ui.task.details;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.workspace.internal.ui.task.details.subtask.TaskSubTaskInfo;

public class TaskDetailsContainer extends Composite {
	private static final long serialVersionUID = -8224474950844756812L;
	
	private RumController rumController;
	
	private TaskDetailsComposite taskDetailsComposite;
	
	private TaskGeneralInfo taskGeneralInfo;
	private List<TaskSubTaskInfo> taskSubTaskInfoList;

	public TaskDetailsContainer(TaskDetailsComposite taskDetailsComposite, RumController rumController) {
		super(taskDetailsComposite, SWT.NONE);
		
		//TODO: Make UI updatable
		
		this.rumController=rumController;
		
		this.taskDetailsComposite=taskDetailsComposite;
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new StackLayout());
		
		this.taskGeneralInfo = new TaskGeneralInfo(this, rumController);
		
		this.taskSubTaskInfoList = new ArrayList<TaskSubTaskInfo>();
		
		((StackLayout)this.getLayout()).topControl = taskGeneralInfo;
		this.layout();
	}

	public void showGeneralInfo() {
		((StackLayout)this.getLayout()).topControl = taskGeneralInfo;
		this.layout();
	}

	public void showSubTaskInfo(int subTaskIndex, SubTask subTask) {
		if (subTaskIndex < taskSubTaskInfoList.size()) {
			((StackLayout)this.getLayout()).topControl = taskSubTaskInfoList.get(subTaskIndex);
		} else {
			TaskSubTaskInfo newTaskSubTaskInfo = new TaskSubTaskInfo(this, rumController, subTask);
			((StackLayout)this.getLayout()).topControl = newTaskSubTaskInfo;
			taskSubTaskInfoList.add(newTaskSubTaskInfo);
		}
		this.layout();
	}
	
	public TaskDetailsComposite getTaskDetailsComposite() {
		return taskDetailsComposite;
	}
}
