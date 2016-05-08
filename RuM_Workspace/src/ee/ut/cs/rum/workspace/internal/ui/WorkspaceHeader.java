package ee.ut.cs.rum.workspace.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.workspace.internal.ui.project.ProjectSelectorCombo;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

public class WorkspaceHeader extends Composite {
	private static final long serialVersionUID = -6786154655313853465L;

	private Label projectTitle;
	private ProjectSelectorCombo projectSelectorCombo;

	public WorkspaceHeader(WorkspaceUI workspaceUI) {
		super(workspaceUI, SWT.NONE);

		this.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		this.setLayout(new GridLayout(3, false));

		projectTitle = new Label(this, SWT.NONE);
		projectTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

		Label projectSelectorLabel = new Label(this, SWT.NONE);
		projectSelectorLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true));
		projectSelectorLabel.setText("Select project: ");

		this.projectSelectorCombo = new ProjectSelectorCombo(this, workspaceUI);
		
		projectTitle.setText("Workspace overview");
	}

	public void setProjectTitle(String titleString) {
		projectTitle.setText(titleString);
	}
	
	public ProjectSelectorCombo getProjectSelectorCombo() {
		return projectSelectorCombo;
	}
}
