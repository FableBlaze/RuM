package ee.ut.cs.rum.workspace.internal.ui.project;

import java.util.List;

import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.workspace.internal.ui.task.TasksTableViewer;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskDetails;

public class ProjectDetailsTabContents extends Composite {
	private static final long serialVersionUID = 1649148279320216160L;

	private Project project;
	private TasksTableViewer tasksTableViewer;

	ProjectDetailsTabContents(ProjectTabFolder projectTabFolder, Composite projectContainer, Project project) {
		super(projectTabFolder, SWT.NONE);

		this.project=project;

		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label l = new Label(this, SWT.NONE);
		if (project.getDescription()!=null) {
			l.setText(project.getDescription());
		}
		l.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		((GridData) l.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;


		tasksTableViewer = new TasksTableViewer(this, projectTabFolder);
		((GridData) tasksTableViewer.getTable().getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;

		Button refreshTasksTableButton = new Button(this, SWT.PUSH);
		refreshTasksTableButton.setText("Refresh tasks table");
		refreshTasksTableButton.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false));

		refreshTasksTableButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -4798041339203828187L;

			@Override
			public void handleEvent(Event arg0) {
				List<Task> projectTasks = TaskAccess.getProjectTasksDataFromDb(project.getId());
				tasksTableViewer.setInput(projectTasks);
			}
		});

		Button addTaskDialogueButton = new Button(this, SWT.PUSH);
		addTaskDialogueButton.setText("Add task");
		addTaskDialogueButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, false));

		addTaskDialogueButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -7320478204056667508L;

			@Override
			public void handleEvent(Event arg0) {
				CTabItem cTabItem = null;

				for (CTabItem c : projectTabFolder.getItems()) {
					if (c.getControl().getClass() == NewTaskDetails.class) {
						cTabItem = c;
						projectTabFolder.setSelection(c);
					}
				}

				if (cTabItem == null) {
					cTabItem = new CTabItem (projectTabFolder, SWT.CLOSE);
					cTabItem.setText ("New task");
					cTabItem.setControl(new NewTaskDetails(projectTabFolder));
					projectTabFolder.setSelection(cTabItem);
					
					//To allow updating "New task" tab after file upload
					ServerPushSession pushSession = new ServerPushSession();
					pushSession.start();

					cTabItem.addDisposeListener(new DisposeListener() {
						private static final long serialVersionUID = 1662616459204440883L;

						@Override
						public void widgetDisposed(DisposeEvent arg0) {
							pushSession.stop();
						}
					});
				}
			}
		});
	}

	public Project getProject() {
		return project;
	}

	public TasksTableViewer getTasksTableViewer() {
		return tasksTableViewer;
	}

}