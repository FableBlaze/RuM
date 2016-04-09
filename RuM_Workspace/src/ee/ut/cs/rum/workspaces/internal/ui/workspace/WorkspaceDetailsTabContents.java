package ee.ut.cs.rum.workspaces.internal.ui.workspace;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.database.domain.Workspace;
import ee.ut.cs.rum.workspaces.internal.ui.task.TasksTableViewer;
import ee.ut.cs.rum.workspaces.internal.ui.task.newtask.NewTaskDetails;

public class WorkspaceDetailsTabContents extends Composite {
	private static final long serialVersionUID = 1649148279320216160L;

	private Workspace workspace;
	private TasksTableViewer tasksTableViewer;

	WorkspaceDetailsTabContents(WorkspaceTabFolder workspaceTabFolder, Composite workspaceContainer, Workspace workspace) {
		super(workspaceTabFolder, SWT.NONE);

		this.workspace=workspace;

		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label l = new Label(this, SWT.BORDER);
		if (workspace.getName()!=null) {
			l.setText(workspace.getName());
		}
		l = new Label(this, SWT.BORDER);
		if (workspace.getDescription()!=null) {
			l.setText(workspace.getDescription());
		}

		tasksTableViewer = new TasksTableViewer(this, workspaceTabFolder);

		Button addTaskDialogueButton = new Button(this, SWT.PUSH);
		addTaskDialogueButton.setText("Add task");
		addTaskDialogueButton.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false));

		addTaskDialogueButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event arg0) {
				CTabItem cTabItem = null;

				for (CTabItem c : workspaceTabFolder.getItems()) {
					if (c.getControl().getClass() == NewTaskDetails.class) {
						cTabItem = c;
						workspaceTabFolder.setSelection(c);
					}
				}

				if (cTabItem == null) {
					cTabItem = new CTabItem (workspaceTabFolder, SWT.CLOSE);
					cTabItem.setText ("New task");
					cTabItem.setControl(new NewTaskDetails(workspaceTabFolder));
					workspaceTabFolder.setSelection(cTabItem);
				}
			}
		});
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public TasksTableViewer getTasksTableViewer() {
		return tasksTableViewer;
	}

}
