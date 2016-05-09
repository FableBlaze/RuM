package ee.ut.cs.rum.workspace.internal.ui.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import ee.ut.cs.rum.workspace.internal.ui.task.TasksTableViewer;

public class ProjectOverviewExpandBar  extends ExpandBar {
	private static final long serialVersionUID = 8709964958003636465L;
	
	private ProjectOverviewComposite projectOverviewComposite;
	private TasksTableViewer tasksTableViewer;
	private ExpandItem tasksTableItem;

	public ProjectOverviewExpandBar(ProjectOverviewComposite projectOverviewComposite) {
		super(projectOverviewComposite, SWT.V_SCROLL);

		this.projectOverviewComposite=projectOverviewComposite;
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		StatisticsComposite statisticsComposite = new StatisticsComposite(this);
		ExpandItem statisticsItem = new ExpandItem (this, SWT.NONE);
		statisticsItem.setText("Statistics");
		statisticsItem.setHeight(statisticsComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		statisticsItem.setControl(statisticsComposite);
		
		ProjectTabFolder projectTabFolder = projectOverviewComposite.getProjectTabFolder();
		tasksTableViewer = new TasksTableViewer(this, projectOverviewComposite, projectTabFolder);
		tasksTableItem = new ExpandItem (this, SWT.NONE);
		tasksTableItem.setText("Tasks");
		tasksTableItem.setHeight(tasksTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		tasksTableItem.setControl(tasksTableViewer.getTable());
		
		tasksTableItem.setExpanded(true);
	}
	
	public ProjectOverviewComposite getProjectOverviewComposite() {
		return projectOverviewComposite;
	}
	
	public TasksTableViewer getTasksTableViewer() {
		return tasksTableViewer;
	}
	
	public ExpandItem getTasksTableItem() {
		return tasksTableItem;
	}
}
