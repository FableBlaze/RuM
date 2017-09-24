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
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.util.ProjectAccess;
import ee.ut.cs.rum.database.util.SubTaskAccess;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.database.util.UserFileAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class WorkspaceOverviewDetails extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = -5990558506997308715L;

	private Display display;
	private RumController rumController;

	private Label totalProjects;
	private Label totalTasks;
	private Label totalSubTasks;
	private Label totalUserFiles;

	WorkspaceOverviewDetails(WorkspaceDetailsContainer workspaceDetailsContainer, RumController rumController) {
		super(workspaceDetailsContainer, SWT.NONE);

		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.PROJECT);
		rumController.registerView(this, ControllerEntityType.TASK);
		rumController.registerView(this, ControllerEntityType.USER_FILE);

		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2, false));

		Label label = new Label (this, SWT.NONE);
		label.setText("Number of projects:");
		totalProjects = new Label (this, SWT.NONE);
		totalProjects.setText(Long.toString(ProjectAccess.getProjectsCountFromDb()));
		label = new Label (this, SWT.NONE);
		label.setText("Number of tasks:");
		totalTasks = new Label (this, SWT.NONE);
		totalTasks.setText(Long.toString(TaskAccess.getTasksCountFromDb()));
		label = new Label (this, SWT.NONE);
		label.setText("Number of sub-tasks:");
		totalSubTasks = new Label (this, SWT.NONE);
		totalSubTasks.setText(Long.toString(SubTaskAccess.getSubTasksCountFromDb()));
		label = new Label (this, SWT.NONE);
		label.setText("Number of files:");
		totalUserFiles = new Label (this, SWT.NONE);
		totalUserFiles.setText(Long.toString(UserFileAccess.getUserFilesCountFromDb()));
		
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof Project) {
			controllerUpdateNotifyProject(updateType);
		} else if (updatedEntity instanceof Task) {
			controllerUpdateNotifyTask(updateType, (Task) updatedEntity);
		} else if (updatedEntity instanceof UserFile) {
			controllerUpdateNotifyUserFile(updateType);
		}
	}

	private void controllerUpdateNotifyProject(ControllerUpdateType updateType) {
		display.asyncExec(new Runnable() {
			public void run() {
				long totalProjectsCount = Long.parseLong(totalProjects.getText());
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
				totalProjects.setText(Long.toString(totalProjectsCount));							
			}
		});
	}

	private void controllerUpdateNotifyTask(ControllerUpdateType updateType, Task task) {
		display.asyncExec(new Runnable() {
			public void run() {
				long totalTasksCount = Long.parseLong(totalTasks.getText());
				long totalSubTasksCount = Long.parseLong(totalSubTasks.getText());
				switch (updateType) {
				case CREATE:
					totalTasksCount+=1;
					totalSubTasksCount+=task.getSubTasks().size();
					break;
				case DELETE:
					totalTasksCount-=1;
					totalSubTasksCount-=task.getSubTasks().size();
					break;
				default:
					break;
				}
				totalTasks.setText(Long.toString(totalTasksCount));
				totalSubTasks.setText(Long.toString(totalSubTasksCount));
			}
		});
	}
	
	private void controllerUpdateNotifyUserFile(ControllerUpdateType updateType) {
		display.asyncExec(new Runnable() {
			public void run() {
				long totaluserFilesCount = Long.parseLong(totalUserFiles.getText());
				switch (updateType) {
				case CREATE:
					totaluserFilesCount+=1;
					break;
				case DELETE:
					totaluserFilesCount-=1;
					break;
				default:
					break;
				}
				totalUserFiles.setText(Long.toString(totaluserFilesCount));							
			}
		});
	}

	@Override
	public void dispose() {
		rumController.unregisterView(this, ControllerEntityType.PROJECT);
		rumController.unregisterView(this, ControllerEntityType.TASK);
		rumController.unregisterView(this, ControllerEntityType.USER_FILE);
		super.dispose();
	}

}
