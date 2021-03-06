package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.workspace.internal.Activator;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.sidebar.NewTaskDetailsSideBar;

public class NewTaskComposite extends Composite {
	private static final long serialVersionUID = -4167600812621979994L;

	private ProjectTabFolder projectTabFolder;
	
	private NewTaskDetailsSideBar detailsSideBar;
	private NewTaskDetailsContainer newTaskDetailsContainer;
	private NewTaskFooter newTaskFooter;
	private Task task;

	public NewTaskComposite(ProjectTabFolder projectTabFolder, Task task, RumController rumController) {
		super(projectTabFolder, SWT.NONE);

		this.projectTabFolder=projectTabFolder;
		this.task=task;

		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.detailsSideBar = new NewTaskDetailsSideBar(this, rumController);

		this.newTaskDetailsContainer = new NewTaskDetailsContainer(this, rumController);

		this.newTaskFooter = new NewTaskFooter(this, rumController);
		((GridData) newTaskFooter.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
	}

	public void initializeBasedOnTaskId(Long taskId) {
		Task baseTask = TaskAccess.getTaskDataFromDb(taskId);
		Activator.getLogger().info("Initializing new task based on task: " + baseTask.toString());
		detailsSideBar.initializeBasedOnTask(baseTask);
		newTaskDetailsContainer.initializeBasedOnTask(baseTask);
		newTaskFooter.setStartTaskButtonEnabled(true);
		newTaskFooter.setRemoveAllSubTasksButtonEnabled(true);
	}
	
	public ProjectTabFolder getProjectTabFolder() {
		return projectTabFolder;
	}
	
	public Task getTask() {
		return task;
	}
	
	public NewTaskDetailsSideBar getDetailsSideBar() {
		return detailsSideBar;
	}

	public NewTaskDetailsContainer getNewTaskDetailsContainer() {
		return newTaskDetailsContainer;
	}
	
	public NewTaskFooter getNewTaskFooter() {
		return newTaskFooter;
	} 
}
