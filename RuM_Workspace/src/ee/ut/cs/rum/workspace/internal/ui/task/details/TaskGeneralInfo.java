package ee.ut.cs.rum.workspace.internal.ui.task.details;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.enums.SubTaskStatus;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class TaskGeneralInfo extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = 684141659312417380L;
	
	private Display display;
	private RumController rumController;
	
	private Task task;
	private Label taskName;
	private Label taskDescription;
	private Label subTasksProcessed;
	private Label taskStatus;
	private Label taskCreatedAt;
	private Label taskLastModifiedAt;

	public TaskGeneralInfo(TaskDetailsContainer taskDetailsContainer, RumController rumController) {
		super(taskDetailsContainer, SWT.NONE);
		
		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.TASK);

		task = taskDetailsContainer.getTaskDetailsComposite().getTask();
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3, false));
		
		Label label;
		
		label = new Label(this, SWT.NONE);
		label.setText("Task name:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskName = new Label(this, SWT.NONE);
		taskName.setText(task.getName());
		taskName.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
				
		FilesTableViewer filesTableViewer = new FilesTableViewer(this, rumController);
		filesTableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		((GridData) filesTableViewer.getTable().getLayoutData()).verticalSpan = 6;
		
		label = new Label(this, SWT.NONE);
		label.setText("Task description:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskDescription = new Label(this, SWT.NONE);
		taskDescription.setText(task.getDescription());
		taskDescription.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Sub-tasks processed:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		subTasksProcessed = new Label(this, SWT.NONE);
		setSubTasksProcessedText();
		subTasksProcessed.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Task status:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskStatus = new Label(this, SWT.NONE);
		taskStatus.setText(task.getStatus().toString());
		taskStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Created at:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskCreatedAt = new Label(this, SWT.NONE);
		taskCreatedAt.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(task.getCreatedAt()));
		taskCreatedAt.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Last change at:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskLastModifiedAt = new Label(this, SWT.NONE);
		taskLastModifiedAt.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(task.getLastModifiedAt()));
		taskLastModifiedAt.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		Label subTaskGraphComposite = new Label(this, SWT.NONE);
		subTaskGraphComposite.setText("Sub-task graph (TODO)");
		subTaskGraphComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		((GridData) subTaskGraphComposite.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
	}
	
	private void setSubTasksProcessedText() {
		int subTasksTotal = task.getSubTasks().size();
		int completedSubTasks = 0;
		for (SubTask subTask : task.getSubTasks()) {
			if (subTask.getStatus()==SubTaskStatus.DONE || subTask.getStatus()==SubTaskStatus.FAILED) {
				completedSubTasks+=1;
			}
		}
		subTasksProcessed.setText(Integer.toString(completedSubTasks) + " of " + Integer.toString(subTasksTotal));	
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
							taskName.setText(updatedTask.getName());
							taskDescription.setText(updatedTask.getDescription());
							taskStatus.setText(updatedTask.getStatus().toString());
							taskLastModifiedAt.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(updatedTask.getLastModifiedAt()));
							setSubTasksProcessedText();
						}
					});
					break;
				case DELETE:
					display.asyncExec(new Runnable() {
						public void run() {
							//TODO: Display a message to user
							TaskGeneralInfo.this.dispose();
						}
					});
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void dispose() {
		rumController.unregisterView(this, ControllerEntityType.TASK);
		super.dispose();
	}

	public Task getTask() {
		return task;
	}
}
