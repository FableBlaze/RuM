package ee.ut.cs.rum.workspace.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

public class ProjectsOverviewExpandBar extends ExpandBar {
	private static final long serialVersionUID = 1583353374480024937L;
	
	private ProjectsTableViewer projectsTableViewer;

	public ProjectsOverviewExpandBar(ProjectsOverview projectsOverview) {
		super(projectsOverview, SWT.V_SCROLL);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		StatisticsComposite statisticsComposite = new StatisticsComposite(this);
		ExpandItem statisticsItem = new ExpandItem (this, SWT.NONE, 0);
		statisticsItem.setText("Statistics");
		statisticsItem.setHeight(statisticsComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		statisticsItem.setControl(statisticsComposite);
		
		this.projectsTableViewer = new ProjectsTableViewer(projectsOverview, this);
		ExpandItem projectsTableItem = new ExpandItem (this, SWT.NONE, 1);
		projectsTableItem.setText("Projects");
		//TODO: Height is calculate wrong on first page load. Fixes itself on page reload
		//TODO: Recalculate size when new project added
		projectsTableItem.setHeight(projectsTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		projectsTableItem.setControl(projectsTableViewer.getTable());
		
		projectsTableItem.setExpanded(true);
	}
	
	public ProjectsTableViewer getProjectsTableViewer() {
		return projectsTableViewer;
	}
}
