package ee.ut.cs.rum.workspace.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.util.ProjectAccess;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class WorkspaceOverviewDetails extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = -5990558506997308715L;

	private Display display;
	private RumController rumController;

	private Label totalProjects;
	private Label totalTasks;

	WorkspaceOverviewDetails(WorkspaceDetailsContainer workspaceDetailsContainer, RumController rumController) {
		super(workspaceDetailsContainer, SWT.NONE);

		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.PROJECT);
		rumController.registerView(this, ControllerEntityType.TASK);

		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2, false));

		Label label = new Label (this, SWT.NONE);
		label.setText("Number of projects:");
		totalProjects = new Label (this, SWT.NONE);
		totalProjects.setText(Integer.toString(ProjectAccess.getProjectsDataFromDb().size()));
		label = new Label (this, SWT.NONE);
		label.setText("Number of tasks:");
		totalTasks = new Label (this, SWT.NONE);
		totalTasks.setText(Integer.toString(TaskAccess.getTasksDataFromDb().size()));
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof Project) {
			controllerUpdateNotifyProject(updateType);
		} else if (updatedEntity instanceof Task) {
			controllerUpdateNotifyTask(updateType);
		}

	}

	private void controllerUpdateNotifyProject(ControllerUpdateType updateType) {
		display.asyncExec(new Runnable() {
			public void run() {
				int totalProjectsCount = Integer.parseInt(totalProjects.getText());
				switch (updateType) {
				case CREATE:
					totalProjectsCount+=1;
					break;
				case DELETE:
					totalProjectsCount-=1;
					break;
				default:
					break;
				}
				totalProjects.setText(Integer.toString(totalProjectsCount));							
			}
		});
	}

	private void controllerUpdateNotifyTask(ControllerUpdateType updateType) {
		display.asyncExec(new Runnable() {
			public void run() {
				int totalTasksCount = Integer.parseInt(totalTasks.getText());
				switch (updateType) {
				case CREATE:
					totalTasksCount+=1;
					break;
				case DELETE:
					totalTasksCount-=1;
					break;
				default:
					break;
				}
				totalTasks.setText(Integer.toString(totalTasksCount));							
			}
		});

	}

	@Override
	public void dispose() {
		rumController.unregisterView(this, ControllerEntityType.PROJECT);
		rumController.unregisterView(this, ControllerEntityType.TASK);
		super.dispose();
	}

}
