package ee.ut.cs.rum.workspace.internal.ui.project;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.project.details.ProjectTaskDetails;

public class ProjectDetailsContainer extends Composite {
	private static final long serialVersionUID = 778865123519656925L;
	
	private RumController rumController;
	
	private ProjectOverview projectOverview;
	private ProjectOverviewDetails projectOverviewDetails;
	
	private List<Long> selectedTaskIds;
	private List<ProjectTaskDetails> workspaceProjectDetailsList;

	public ProjectDetailsContainer(ProjectOverview projectOverview, RumController rumController) {
		super(projectOverview, SWT.NONE);
		
		this.rumController=rumController;
		
		this.projectOverview=projectOverview;
		
		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new StackLayout());
		
		projectOverviewDetails = new ProjectOverviewDetails(this, rumController);
		
		this.selectedTaskIds = new ArrayList<Long>();
		this.workspaceProjectDetailsList = new ArrayList<ProjectTaskDetails>();
		
		((StackLayout)this.getLayout()).topControl = projectOverviewDetails;
		this.layout();
	}
	
	public void showDetailsOf(Long taskId) {
		if (taskId==null) {
			((StackLayout)this.getLayout()).topControl = projectOverviewDetails;
		} else {
			int selectedTaskId = selectedTaskIds.indexOf(taskId);
			if (selectedTaskId==-1) {
				selectedTaskIds.add(taskId);
				ProjectTaskDetails projectTaskDetails = new ProjectTaskDetails(this, taskId, rumController);
				workspaceProjectDetailsList.add(projectTaskDetails);
				((StackLayout)this.getLayout()).topControl = projectTaskDetails;
			} else {
				((StackLayout)this.getLayout()).topControl = workspaceProjectDetailsList.get(selectedTaskId);
			}
		}
		this.layout();
	}
	
	public ProjectOverview getProjectOverview() {
		return projectOverview;
	}
}
