package ee.ut.cs.rum.workspace.internal.ui.project.taskstable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectOverviewComposite;

public class TasksTableComposite extends Composite {
	private static final long serialVersionUID = -2229297948236990364L;
	
	private TasksTableViewer tasksTableViewer;
	private TasksTableFilter tasksTableFilter;
	private ProjectOverviewComposite projectOverviewComposite;
	
	public TasksTableComposite(ProjectOverviewComposite projectOverviewComposite, RumController rumController) {
		super(projectOverviewComposite, SWT.NONE);
		
		this.projectOverviewComposite=projectOverviewComposite;
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		this.setLayout(new GridLayout(2, false));
		
		Label taskSearchLabel = new Label(this, SWT.NONE);
		taskSearchLabel.setText("Filter tasks: ");
		taskSearchLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		
		Text taskSearchInput = new Text(this, SWT.BORDER);
		taskSearchInput.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		taskSearchInput.addKeyListener(new KeyAdapter() {
			private static final long serialVersionUID = -540605670370011877L;

			public void keyReleased(KeyEvent ke) {
				((TasksTableFilter) tasksTableFilter).setSearchText(taskSearchInput.getText());
				tasksTableViewer.refresh();
			}
		});
		
		this.tasksTableViewer = new TasksTableViewer(this, rumController);
		tasksTableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		((GridData) this.tasksTableViewer.getTable().getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
		
		tasksTableViewer.addSelectionChangedListener(new TaskSelectionChangedListener());
		
		this.tasksTableFilter = new TasksTableFilter();
		this.tasksTableViewer.addFilter(tasksTableFilter);
	}

	public ProjectOverviewComposite getProjectOverviewComposite() {
		return projectOverviewComposite;
	}
}
