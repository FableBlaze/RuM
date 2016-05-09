package ee.ut.cs.rum.workspace.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

public class ProjectsOverviewExpandBar extends ExpandBar {
	private static final long serialVersionUID = 1583353374480024937L;
	
	private ProjectsTableViewer projectsTableViewer;

	//TODO: Height of tables is calculated wrong on first page load. Fixes itself on page reload
	//TODO: Recalculate height of tables when new project added
	public ProjectsOverviewExpandBar(ProjectsOverview projectsOverview) {
		super(projectsOverview, SWT.V_SCROLL);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		StatisticsComposite statisticsComposite = new StatisticsComposite(this);
		ExpandItem statisticsItem = new ExpandItem (this, SWT.NONE, 0);
		statisticsItem.setText("Statistics");
		statisticsItem.setHeight(statisticsComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		statisticsItem.setControl(statisticsComposite);
		
		this.projectsTableViewer = new ProjectsTableViewer(this, projectsOverview);
		ExpandItem projectsTableItem = new ExpandItem (this, SWT.NONE, 1);
		projectsTableItem.setText("Projects");
		projectsTableItem.setHeight(projectsTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		projectsTableItem.setControl(projectsTableViewer.getTable());
		
		PluginsTableViewer pluginsTableViewer = new PluginsTableViewer(this);
		ExpandItem pluginsTableItem = new ExpandItem (this, SWT.NONE, 2);
		pluginsTableItem.setText("Plugins");
		pluginsTableItem.setHeight(pluginsTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		pluginsTableItem.setControl(pluginsTableViewer.getTable());
		
		projectsTableItem.setExpanded(true);
	}
	
	public ProjectsTableViewer getProjectsTableViewer() {
		return projectsTableViewer;
	}
}
