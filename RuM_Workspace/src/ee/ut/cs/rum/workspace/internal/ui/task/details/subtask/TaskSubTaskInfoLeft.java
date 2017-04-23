package ee.ut.cs.rum.workspace.internal.ui.task.details.subtask;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.workspace.internal.ui.task.PluginInfoComposite;

public class TaskSubTaskInfoLeft extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = -3780211750244322039L;

	private Display display;
	private RumController rumController;

	private SubTask subTask;
	private Label subTaskName;
	private Label subTaskDescription;
	private Label subTaskStatus;
	private Label subTaskLastModifiedAt;
	private Label subTaskCreatedAt;

	public TaskSubTaskInfoLeft(TaskSubTaskInfo taskSubTaskInfo, RumController rumController, SubTask subTask) {
		super(taskSubTaskInfo, SWT.NONE);

		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.SUBTASK);

		this.subTask=subTask;

		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

		Label label;

		label = new Label(this, SWT.NONE);
		label.setText("Sub-task Info");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false));
		((GridData) label.getLayoutData()).horizontalSpan = ((GridLayout)this.getLayout()).numColumns;

		label = new Label(this, SWT.NONE);
		label.setText("Sub-task name:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		subTaskName = new Label(this, SWT.NONE);
		subTaskName.setText(subTask.getName());
		subTaskName.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));

		label = new Label(this, SWT.NONE);
		label.setText("Sub-task decription:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		subTaskDescription = new Label(this, SWT.NONE);
		subTaskDescription.setText(subTask.getDescription());
		subTaskDescription.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));

		label = new Label(this, SWT.NONE);
		label.setText("Sub-task status:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		subTaskStatus = new Label(this, SWT.NONE);
		subTaskStatus.setText(subTask.getStatus().toString());
		subTaskStatus.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));

		label = new Label(this, SWT.NONE);
		label.setText("Created at:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		subTaskCreatedAt = new Label(this, SWT.NONE);
		subTaskCreatedAt.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(subTask.getCreatedAt()));
		subTaskCreatedAt.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Last change at:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		subTaskLastModifiedAt = new Label(this, SWT.NONE);
		subTaskLastModifiedAt.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(subTask.getLastModifiedAt()));
		subTaskLastModifiedAt.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));

		PluginInfoComposite pluginInfoComposite = new PluginInfoComposite(this);
		pluginInfoComposite.updateSelectedPluginInfo(subTask.getPlugin());
		pluginInfoComposite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		((GridData) pluginInfoComposite.getLayoutData()).horizontalSpan = ((GridLayout)this.getLayout()).numColumns;
		
		FilesTableViewer filesTableViewer = new FilesTableViewer(this, rumController);
		filesTableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		((GridData) filesTableViewer.getTable().getLayoutData()).horizontalSpan = ((GridLayout)this.getLayout()).numColumns;
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof SubTask) {
			SubTask updatedSubTask = (SubTask) updatedEntity;
			if (updatedSubTask.getId()==subTask.getId()) {
				switch (updateType) {
				//Subtasks will not be added nor removed after task creation
				case MODIFIY:
					display.asyncExec(new Runnable() {
						public void run() {
							subTaskName.setText(updatedSubTask.getName());
							subTaskDescription.setText(updatedSubTask.getDescription());
							subTaskStatus.setText(updatedSubTask.getStatus().toString());
							subTaskLastModifiedAt.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(updatedSubTask.getLastModifiedAt()));
						}
					});
					break;
				case DELETE:
					display.asyncExec(new Runnable() {
						public void run() {
							//TODO: Display a message to user
							TaskSubTaskInfoLeft.this.dispose();
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
		rumController.unregisterView(this, ControllerEntityType.SUBTASK);
		super.dispose();
	}
	
	public SubTask getSubTask() {
		return subTask;
	}

}
