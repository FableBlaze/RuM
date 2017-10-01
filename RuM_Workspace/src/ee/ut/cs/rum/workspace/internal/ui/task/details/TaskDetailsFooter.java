package ee.ut.cs.rum.workspace.internal.ui.task.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskComposite;

public class TaskDetailsFooter extends Composite {
	private static final long serialVersionUID = 8793166001814894685L;

	public TaskDetailsFooter(TaskDetailsComposite taskDetailsComposite, RumController rumController) {
		super(taskDetailsComposite, SWT.NONE);
		
		this.setLayout(new GridLayout(1, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		Button useForNewTaskButton = new Button(this, SWT.PUSH);
		useForNewTaskButton.setText("Use for new task");
		useForNewTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		useForNewTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -2883376239843215957L;

			@Override
			public void handleEvent(Event arg0) {
				ProjectTabFolder projectTabFolder = taskDetailsComposite.getProjectTabFolder();
				for (CTabItem c : projectTabFolder.getItems()) {
					if (c.getControl().getClass() == NewTaskComposite.class) {
						c.dispose();
					}
				}
				CTabItem cTabItem = new CTabItem (projectTabFolder, SWT.CLOSE);
				cTabItem.setText ("New task");
				Task task = new Task();
				task.setName("(New task)");
				task.setDescription("");
				task.setProject(projectTabFolder.getProject());
				task.setStatus(TaskStatus.NEW);
				NewTaskComposite newTaskComposite = new NewTaskComposite(projectTabFolder, task, rumController);
				newTaskComposite.initializeBasedOnTaskId(taskDetailsComposite.getTask().getId());
				cTabItem.setControl(newTaskComposite);
				projectTabFolder.setSelection(cTabItem);
			}
		});
	}
}
