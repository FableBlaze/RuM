package ee.ut.cs.rum.workspace.internal.ui.project;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class ProjectOverviewDetails extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = 1551715238795937967L;
	
	private Display display;
	private RumController rumController;
	
	private Project project;
	private Label projectName;
	private Label projectDescription;
	private Label createdAt;
	private Label lastChangeAt;

	public ProjectOverviewDetails(ProjectDetailsContainer projectDetailsContainer, RumController rumController) {
		super(projectDetailsContainer, SWT.NONE);
		
		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.PROJECT);
		
		this.project=projectDetailsContainer.getProjectOverview().getProject();
		
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
		
		
	}
	
	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		rumController.unregisterView(this, ControllerEntityType.PROJECT);
		super.dispose();
	}
}
