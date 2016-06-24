package ee.ut.cs.rum.workspace.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

public class WorkspaceHeader extends Composite {
	private static final long serialVersionUID = -6786154655313853465L;

	private Label headerTitle;
	private ProjectSelectorCombo projectSelectorCombo;

	public WorkspaceHeader(WorkspaceUI workspaceUI, RumController rumController) {
		super(workspaceUI, SWT.NONE);

		this.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		this.setLayout(new GridLayout(3, false));

		headerTitle = new Label(this, SWT.NONE);
		headerTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

		this.projectSelectorCombo = new ProjectSelectorCombo(this, workspaceUI, rumController);
		
		setHeaderTitle("Workspace overview");
	}

	public void setHeaderTitle(String titleString) {
		headerTitle.setText(titleString);
	}
	
	public ProjectSelectorCombo getProjectSelectorCombo() {
		return projectSelectorCombo;
	}
}
