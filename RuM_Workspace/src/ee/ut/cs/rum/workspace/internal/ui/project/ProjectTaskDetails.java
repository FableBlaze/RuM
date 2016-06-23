package ee.ut.cs.rum.workspace.internal.ui.project;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.workspace.internal.ui.task.details.TaskDetails;
import ee.ut.cs.rum.workspace.internal.ui.task.details.TaskDetailsComposite;

public class ProjectTaskDetails extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = 6334776047604763250L;

	private Display display;
	private RumController rumController;

	private ProjectTabFolder projectTabFolder;
	private Task task;
	private Label taskName;
	private Label taskDescription;
	private Label taskSubTasks;
	private Label taskStatus;
	private Label createdAt;
	private Label lastChangeAt;


	public ProjectTaskDetails(ProjectDetailsContainer projectDetailsContainer, Task task, RumController rumController) {
		super(projectDetailsContainer, SWT.NONE);

		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.TASK);

		this.projectTabFolder = projectDetailsContainer.getProjectOverview().getProjectTabFolder();
		this.task=task;

		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2, false));


		Label nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Task name:");
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskName = new Label(this, SWT.NONE);
		taskName.setText(task.getName());
		taskName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Task description:");
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskDescription = new Label(this, SWT.NONE);
		taskDescription.setText(task.getDescription());
		taskDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Sub-tasks:");
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskSubTasks = new Label(this, SWT.NONE);
		taskSubTasks.setText("TODO");
		taskSubTasks.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Task status:");
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskStatus = new Label(this, SWT.NONE);
		taskStatus.setText(task.getStatus().toString());
		taskStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Created at:");
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		createdAt = new Label(this, SWT.NONE);
		createdAt.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(task.getCreatedAt()));
		createdAt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Last change at:");
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		lastChangeAt = new Label(this, SWT.NONE);
		lastChangeAt.setText("TODO");
		lastChangeAt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));


		Label subTaskGraph = new Label(this, SWT.NONE);
		subTaskGraph.setText("Sub-task graph (TODO)");
		subTaskGraph.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		((GridData) subTaskGraph.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;

		Button openTaskButton = new Button(this, SWT.BORDER);
		openTaskButton.setText("Open task");
		openTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		((GridData) openTaskButton.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		openTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 5512667422781634707L;

			public void handleEvent(Event e) {
				Long taskId = task.getId();
				CTabItem cTabItem = null;
				
				//Checking if the tab is already open
				for (CTabItem c : projectTabFolder.getItems()) {
					if (c.getControl().getClass() == TaskDetails.class) {
						if (((TaskDetails)c.getControl()).getTaskId() == taskId) {
							cTabItem = c;
							projectTabFolder.setSelection(c);
						}
					}
				}

				if (cTabItem == null) {
					cTabItem = new CTabItem (projectTabFolder, SWT.CLOSE);
					cTabItem.setText ("Task " + taskId.toString());
					cTabItem.setControl(new TaskDetailsComposite(projectTabFolder, rumController, taskId));
					projectTabFolder.setSelection(cTabItem);	
				}
			}
		});
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
							//TODO: Project last change
							lastChangeAt.setText("TODO");
						}
					});
					break;
				case DELETE:
					display.asyncExec(new Runnable() {
						public void run() {
							//TODO: Display a message to user
							ProjectTaskDetails.this.dispose();
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

}
