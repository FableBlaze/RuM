package ee.ut.cs.rum.workspace.internal.ui.project.taskstable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectOverview;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;

public class TasksTableComposite extends Composite {
	private static final long serialVersionUID = -2229297948236990364L;
	
	private TasksTableViewer tasksTableViewer;
	private TasksTableFilter tasksTableFilter;
	private ProjectOverview projectOverview;
	
	public TasksTableComposite(ProjectOverview projectOverview, RumController rumController) {
		super(projectOverview, SWT.NONE);
		
		this.projectOverview=projectOverview;
		
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
		
		Button generalInfoButton = new Button(this, SWT.PUSH);
		generalInfoButton.setText("General info");
		generalInfoButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		((GridData) generalInfoButton.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
		generalInfoButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -9188529455494099314L;
			
			@Override
			public void handleEvent(Event arg0) {
				projectOverview.getProjectDetailsContainer().showDetailsOf(null);
				tasksTableViewer.getTable().deselectAll();
			}
		});
		
		this.tasksTableViewer = new TasksTableViewer(this, rumController);
		tasksTableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		((GridData) this.tasksTableViewer.getTable().getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
		
		tasksTableViewer.addSelectionChangedListener(new TaskSelectionChangedListener(projectOverview));
		
		ProjectTabFolder projectTabFolder = projectOverview.getProjectTabFolder();
		tasksTableViewer.addDoubleClickListener(new TaskRowDoubleClickListener(projectTabFolder, rumController));
		
		this.tasksTableFilter = new TasksTableFilter();
		this.tasksTableViewer.addFilter(tasksTableFilter);
	}

	public ProjectOverview getProjectOverview() {
		return projectOverview;
	}
}
