package ee.ut.cs.rum.workspace.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;

public class WorkspaceDetailsContainer extends Composite {
	private static final long serialVersionUID = 4293543744679080873L;

	public WorkspaceDetailsContainer(WorkspaceOverview workspaceOverview, RumController rumController) {
		super(workspaceOverview, SWT.NONE);
		
		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new StackLayout());
		
		WorkspaceOverviewDetails workspaceOverviewDetails = new WorkspaceOverviewDetails(this, rumController);
		
		((StackLayout)this.getLayout()).topControl = workspaceOverviewDetails;
		this.layout();
	}
}
