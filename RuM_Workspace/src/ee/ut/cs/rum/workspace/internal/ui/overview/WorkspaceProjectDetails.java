package ee.ut.cs.rum.workspace.internal.ui.overview;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class WorkspaceProjectDetails extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = -5990558506997308715L;
	
	private Display display;
	private RumController rumController;

	private Project project;
	private Label projectName;
	private Label projectDescription;
	private Label createdAt;
	private Label lastChangeAt;

	WorkspaceProjectDetails(WorkspaceDetailsContainer workspaceDetailsContainer, Project project, RumController rumController) {
		super(workspaceDetailsContainer, SWT.NONE);
		
		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.PROJECT);
		
		this.project=project;
		
		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2, false));
		
		Label nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Project name:");
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		projectName = new Label(this, SWT.NONE);
		projectName.setText(project.getName());
		projectName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Project description:");
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		projectDescription = new Label(this, SWT.NONE);
		projectDescription.setText(project.getDescription());
		projectDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Created at:");
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		createdAt = new Label(this, SWT.NONE);
		createdAt.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(project.getCreatedAt()));
		createdAt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		nameLabel = new Label(this, SWT.NONE);
		nameLabel.setText("Last change at:");
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		lastChangeAt = new Label(this, SWT.NONE);
		lastChangeAt.setText("TODO");
		lastChangeAt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		Label notificationsTable = new Label(this, SWT.NONE);
		notificationsTable.setText("Project notifications (TODO)");
		notificationsTable.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		((GridData) notificationsTable.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		
		Button openProjectButton = new Button(this, SWT.BORDER);
		openProjectButton.setText("Open project");
		openProjectButton.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		((GridData) openProjectButton.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		openProjectButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 6660200337375128860L;

			public void handleEvent(Event e) {
				workspaceDetailsContainer.getWorkspaceOverview().getWorkspaceUI().getWorkspaceHeader().getProjectSelectorCombo().selectProject(project);
			}
		});
	}
	
	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof Project) {
			Project updatedProject=(Project)updatedEntity;
			if (updatedProject.getId()==project.getId()) {
				switch (updateType) {
				case MODIFIY:
					display.asyncExec(new Runnable() {
						public void run() {
							projectName.setText(updatedProject.getName());
							projectDescription.setText(updatedProject.getDescription());
							//TODO: Project last change
							lastChangeAt.setText("TODO");
						}
					});
					break;
				case DELETE:
					display.asyncExec(new Runnable() {
						public void run() {
							//TODO: Display a message to user
							WorkspaceProjectDetails.this.dispose();
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
		rumController.unregisterView(this, ControllerEntityType.PROJECT);
		super.dispose();
	}
}
