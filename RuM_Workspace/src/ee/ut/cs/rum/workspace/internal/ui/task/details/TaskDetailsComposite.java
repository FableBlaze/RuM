package ee.ut.cs.rum.workspace.internal.ui.task.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;
import ee.ut.cs.rum.workspace.internal.ui.task.details.sidebar.TaskDetailsSidebar;

public class TaskDetailsComposite extends Composite {
	private static final long serialVersionUID = 4750338366397123743L;
	
	private TaskDetailsSidebar detailsSidebar;
	private TaskDetailsContainer taskDetailsContainer;
	private Task task;

	public TaskDetailsComposite(ProjectTabFolder projectTabFolder, RumController rumController, Long taskId) {
		super(projectTabFolder, SWT.NONE);
		
		this.task = TaskAccess.getTaskDataFromDb(taskId);
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.detailsSidebar = new TaskDetailsSidebar(this, rumController);
		
		this.taskDetailsContainer = new TaskDetailsContainer(this, rumController);
		
		TaskDetailsFooter taskDetailsFooter = new TaskDetailsFooter(this);
		((GridData) taskDetailsFooter.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
	}
	
	public TaskDetailsSidebar getDetailsSidebar() {
		return detailsSidebar;
	}
	public TaskDetailsContainer getTaskDetailsContainer() {
		return taskDetailsContainer;
	}
	public Task getTask() {
		return task;
	}
}
