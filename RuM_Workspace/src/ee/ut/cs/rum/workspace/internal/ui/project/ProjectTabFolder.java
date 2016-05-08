package ee.ut.cs.rum.workspace.internal.ui.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.database.domain.Project;

public class ProjectTabFolder extends CTabFolder {
	private static final long serialVersionUID = 3261215361750051333L;
	
	private CTabItem projectDetailsTab;
	private ProjectDetailsTabContents projectDetailsTabContents;
	private Project project;
	
	public ProjectTabFolder(Composite projectContainer, Project project) {
		super(projectContainer, SWT.BORDER);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.project=project;
		
		this.projectDetailsTab = new CTabItem (this, SWT.NONE);
		projectDetailsTab.setText ("Project details");
		
		this.projectDetailsTabContents = new ProjectDetailsTabContents(this, projectContainer, project);
		projectDetailsTab.setControl (projectDetailsTabContents);
		
		this.setSelection(0);
	}
	
	public Project getProject() {
		return project;
	}
	
	public ProjectDetailsTabContents getProjectDetailsTabContents() {
		return projectDetailsTabContents;
	}
}
