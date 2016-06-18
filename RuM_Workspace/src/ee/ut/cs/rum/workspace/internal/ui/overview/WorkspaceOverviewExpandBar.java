package ee.ut.cs.rum.workspace.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.overview.projectstable.ProjectsTableViewer;

public class WorkspaceOverviewExpandBar extends ExpandBar {
	private static final long serialVersionUID = 1583353374480024937L;
	
	private ProjectsTableViewer projectsTableViewer;

	//TODO: Height of tables is calculated wrong on first page load. Fixes itself on page reload
	//TODO: Recalculate height of tables when new project added
	public WorkspaceOverviewExpandBar(WorkspaceOverview workspaceOverview, RumController rumController) {
		super(workspaceOverview, SWT.V_SCROLL);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
//		StatisticsComposite statisticsComposite = new StatisticsComposite(this, rumController);
//		ExpandItem statisticsItem = new ExpandItem (this, SWT.NONE);
//		statisticsItem.setText("Statistics");
//		statisticsItem.setHeight(statisticsComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
//		statisticsItem.setControl(statisticsComposite);
		
//		this.projectsTableViewer = new ProjectsTableViewer(this, workspaceOverview);
//		ExpandItem projectsTableItem = new ExpandItem (this, SWT.NONE);
//		projectsTableItem.setText("Projects");
//		projectsTableItem.setHeight(projectsTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
//		projectsTableItem.setControl(projectsTableViewer.getTable());
		
//		UserFilesTableViewer userFilesTableViewer = new UserFilesTableViewer(this);
//		ExpandItem userFileTableItem = new ExpandItem (this, SWT.NONE);
//		userFileTableItem.setText("Files");
//		userFileTableItem.setHeight(userFilesTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
//		userFileTableItem.setControl(userFilesTableViewer.getTable());
		
//		PluginsTableViewer pluginsTableViewer = new PluginsTableViewer(this);
//		ExpandItem pluginsTableItem = new ExpandItem (this, SWT.NONE);
//		pluginsTableItem.setText("Plugins");
//		pluginsTableItem.setHeight(pluginsTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
//		pluginsTableItem.setControl(pluginsTableViewer.getTable());
		
//		statisticsItem.setExpanded(true);
//		projectsTableItem.setExpanded(true);
	}
	
	public ProjectsTableViewer getProjectsTableViewer() {
		return projectsTableViewer;
	}
}
