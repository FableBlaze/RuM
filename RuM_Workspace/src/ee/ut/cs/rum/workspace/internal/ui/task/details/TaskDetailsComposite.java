package ee.ut.cs.rum.workspace.internal.ui.task.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;
import ee.ut.cs.rum.workspace.internal.ui.task.details.sidebar.TaskDetailsSidebar;

public class TaskDetailsComposite extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = 4750338366397123743L;
	
	private Display display;
	private RumController rumController;
	
	private ProjectTabFolder projectTabFolder;
	
	private TaskDetailsSidebar detailsSidebar;
	private TaskDetailsContainer taskDetailsContainer;
	private TaskDetailsFooter taskDetailsFooter;
	private Task task;

	public TaskDetailsComposite(ProjectTabFolder projectTabFolder, RumController rumController, Long taskId) {
		super(projectTabFolder, SWT.NONE);
		
		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.TASK);
		
		this.projectTabFolder = projectTabFolder;
		
		this.task = TaskAccess.getTaskDataFromDb(taskId);
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.detailsSidebar = new TaskDetailsSidebar(this, rumController);
		
		this.taskDetailsContainer = new TaskDetailsContainer(this, rumController);
		
		taskDetailsFooter = new TaskDetailsFooter(this, rumController);
		((GridData) taskDetailsFooter.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
		if (task.getStatus()!=TaskStatus.DONE) {
			taskDetailsFooter.setEnabled(false);
		}
	}
	
	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof Task) {
			Task updatedTask=(Task)updatedEntity;
			if (updatedTask.getId()==task.getId()) {
				switch (updateType) {
				case MODIFIY:
					display.asyncExec(new Runnable() {
						public void run() {
							if (updatedTask.getStatus()==TaskStatus.DONE) {
								taskDetailsFooter.setEnabled(true);
							}
						}
					});
					break;
				default:
					break;
				}
			}
		}
	}
	
	public ProjectTabFolder getProjectTabFolder() {
		return projectTabFolder;
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
	
	@Override
	public void dispose() {
		rumController.unregisterView(this, ControllerEntityType.TASK);
		super.dispose();
	}
}
