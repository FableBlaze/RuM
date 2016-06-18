package ee.ut.cs.rum.workspace.internal.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.util.ProjectAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.workspace.internal.Activator;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

public class ProjectSelectorCombo extends Combo implements RumUpdatableView {
	private static final long serialVersionUID = -1671918025859199853L;

	private Display display;
	private RumController rumController;

	private List<Project> projects;
	private List<ProjectTabFolder> projectsDetails;
	private WorkspaceUI workspaceUI;
	private WorkspaceHeader workspaceHeader;

	public ProjectSelectorCombo(WorkspaceHeader workspaceHeader, WorkspaceUI workspaceUI, RumController rumController) {
		super(workspaceHeader, SWT.READ_ONLY);

		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.PROJECT);

		this.workspaceUI=workspaceUI;
		this.workspaceHeader=workspaceHeader;

		this.add("Workspace overview");
		this.setVisibleItemCount(10);
		this.select(0);
		updateWorkspaceSelector();

		this.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -1752969541573951231L;

			public void handleEvent(Event e) {
				updateSelectedProjectDetails();
			}
		});
	}

	private void updateWorkspaceSelector() {
		this.projects = new ArrayList<Project>();
		this.projects.add(null);
		this.projects.addAll(ProjectAccess.getProjectsDataFromDb());
		createProjectDetailsList(projects.size());

		for (Project project : projects) {
			if (project!=null) {
				this.add(project.getName());
			}
		}
	}

	public void updateSelectedProjectDetails() {
		int selectedIndex = ProjectSelectorCombo.this.getSelectionIndex();
		StackLayout workspaceContainerLayout = (StackLayout)workspaceUI.getWorkspaceContainer().getLayout();

		if (selectedIndex==0) {
			workspaceContainerLayout.topControl=workspaceUI.getWorkspaceOverview();
		} else {
			ProjectTabFolder selectedWorkspaceDetails = projectsDetails.get(selectedIndex);
			if (selectedWorkspaceDetails==null) {
				selectedWorkspaceDetails = new ProjectTabFolder(workspaceUI.getWorkspaceContainer(), projects.get(selectedIndex));
				projectsDetails.add(selectedIndex, selectedWorkspaceDetails);
			}
			workspaceContainerLayout.topControl=selectedWorkspaceDetails;
		}
		workspaceUI.getWorkspaceContainer().layout();


		if (projects.get(selectedIndex)!=null) {
			workspaceHeader.setHeaderTitle("Project: " + projects.get(selectedIndex).getName());
			Activator.getLogger().info("Opened project: " + projects.get(selectedIndex).toString());
		} else {
			workspaceHeader.setHeaderTitle("Workspace overview");
			Activator.getLogger().info("Opened projects overview");
		}
	}
	

	public void selectProject(Project project) {
		int projectIndex = findProjectIndex(project);
		if (projectIndex != -1) {
			this.select(projectIndex);
			updateSelectedProjectDetails();
		}
		
	}

	private void createProjectDetailsList(int size) {
		this.projectsDetails = new ArrayList<ProjectTabFolder>();
		for (int i = 0; i < size; i++) {
			projectsDetails.add(null);
		}
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof Project) {
			Project project=(Project)updatedEntity;
			int projectIndex;
			switch (updateType) {
			case CREATE:
				this.projects.add(project);
				this.projectsDetails.add(null);
				display.asyncExec(new Runnable() {
					public void run() {
						ProjectSelectorCombo.this.add(project.getName());
					}
				});
				break;
			case MODIFIY:
				projectIndex = findProjectIndex(project);
				if (projectIndex !=-1) {
					display.asyncExec(new Runnable() {
						public void run() {
							ProjectSelectorCombo.this.setItem(projectIndex, project.getName());
						}
					});
				}
				break;
			case DELETE:
				projectIndex = findProjectIndex(project);
				if (projectIndex != -1) {
					synchronized(this){
						this.projects.remove(projectIndex);
						this.projectsDetails.remove(projectIndex);
						display.asyncExec(new Runnable() {
							public void run() {
								ProjectSelectorCombo.this.remove(projectIndex);
							}
						});
					}
				}
				break;
			default:
				break;
			}
		}

	}

	private int findProjectIndex(Project project) {
		for (int i = 1; i < this.projects.size(); i++) {
			if (this.projects.get(i).getId()==project.getId()) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void dispose() {
		rumController.unregisterView(this, ControllerEntityType.PROJECT);
		super.dispose();
	}

}
