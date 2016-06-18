package ee.ut.cs.rum.workspace.internal.ui.overview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Project;

public class WorkspaceDetailsContainer extends Composite {
	private static final long serialVersionUID = 4293543744679080873L;
	
	private WorkspaceOverviewDetails workspaceOverviewDetails;
	
	List<Long> selectedProjectIds;
	List<WorkspaceProjectDetails> workspaceProjectDetailsList;

	public WorkspaceDetailsContainer(WorkspaceOverview workspaceOverview, RumController rumController) {
		super(workspaceOverview, SWT.NONE);
		
		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new StackLayout());
		
		workspaceOverviewDetails = new WorkspaceOverviewDetails(this, rumController);
		
		this.selectedProjectIds = new ArrayList<Long>();
		this.workspaceProjectDetailsList = new ArrayList<WorkspaceProjectDetails>();
		
		((StackLayout)this.getLayout()).topControl = workspaceOverviewDetails;
		this.layout();
	}

	public void showDetailsOf(Project selectedProject) {
		if (selectedProject==null) {
			((StackLayout)this.getLayout()).topControl = workspaceOverviewDetails;
		} else {
			int selectedProjectId = selectedProjectIds.indexOf(selectedProject.getId());
			if (selectedProjectId==-1) {
				selectedProjectIds.add(selectedProject.getId());
				WorkspaceProjectDetails workspaceProjectDetails = new WorkspaceProjectDetails(this, selectedProject);
				workspaceProjectDetailsList.add(workspaceProjectDetails);
				((StackLayout)this.getLayout()).topControl = workspaceProjectDetails;
			} else {
				((StackLayout)this.getLayout()).topControl = workspaceProjectDetailsList.get(selectedProjectId);
			}
		}
		this.layout();
		
	}
}
