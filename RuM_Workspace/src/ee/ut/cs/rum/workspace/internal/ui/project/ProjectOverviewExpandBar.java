package ee.ut.cs.rum.workspace.internal.ui.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

public class ProjectOverviewExpandBar  extends ExpandBar {
	private static final long serialVersionUID = 8709964958003636465L;
	
	private ProjectOverviewComposite projectOverviewComposite;
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
				
		UserFilesTableViewer userFilesTableViewer = new UserFilesTableViewer(this);
		ExpandItem userFileTableItem = new ExpandItem (this, SWT.NONE);
		userFileTableItem.setText("Files");
		userFileTableItem.setHeight(userFilesTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		userFileTableItem.setControl(userFilesTableViewer.getTable());
		
	}
	
	public ProjectOverviewComposite getProjectOverviewComposite() {
		return projectOverviewComposite;
	}
	
	public ExpandItem getTasksTableItem() {
		return tasksTableItem;
	}
}
