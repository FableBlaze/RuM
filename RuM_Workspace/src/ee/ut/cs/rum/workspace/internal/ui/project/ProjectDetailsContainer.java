package ee.ut.cs.rum.workspace.internal.ui.project;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;

public class ProjectDetailsContainer extends Composite {
	private static final long serialVersionUID = 778865123519656925L;
	
	private RumController rumController;
	
	private ProjectOverviewDetails projectOverviewDetails;
	
	private List<Long> selectedProjectIds;
	private List<ProjectTaskDetails> workspaceProjectDetailsList;

	public ProjectDetailsContainer(ProjectOverview projectOverview, RumController rumController) {
		super(projectOverview, SWT.NONE);
		
		this.rumController=rumController;
		
		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new StackLayout());
		
		projectOverviewDetails = new ProjectOverviewDetails(this, rumController);
		
		this.selectedProjectIds = new ArrayList<Long>();
		this.workspaceProjectDetailsList = new ArrayList<ProjectTaskDetails>();
		
		((StackLayout)this.getLayout()).topControl = projectOverviewDetails;
		this.layout();
	}
	
	public void showDetailsOf(Task selectedTask) {
		if (selectedTask==null) {
			((StackLayout)this.getLayout()).topControl = projectOverviewDetails;
		} else {
			int selectedProjectId = selectedProjectIds.indexOf(selectedTask.getId());
			if (selectedProjectId==-1) {
				selectedProjectIds.add(selectedTask.getId());
				ProjectTaskDetails workspaceProjectDetails = new ProjectTaskDetails(this, selectedTask, rumController);
				workspaceProjectDetailsList.add(workspaceProjectDetails);
				((StackLayout)this.getLayout()).topControl = workspaceProjectDetails;
			} else {
				((StackLayout)this.getLayout()).topControl = workspaceProjectDetailsList.get(selectedProjectId);
			}
		}
		this.layout();
	}
}
