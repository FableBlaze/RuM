package ee.ut.cs.rum.workspace.internal.ui.task;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;
import ee.ut.cs.rum.workspace.internal.ui.task.details.TaskDetails;

public class TaskDetailsButton extends Button {
	private static final long serialVersionUID = 8158243533318100884L;
	
	private Long taskId;
	private ProjectTabFolder projectTabFolder;

	public TaskDetailsButton(Composite parent, Long taskId, ProjectTabFolder projectTabFolder) {
		super(parent, SWT.NONE);
		this.taskId = taskId;
		this.projectTabFolder = projectTabFolder;

		this.setText("Details");
		
		this.addSelectionListener(this.createSelectionListener());
	}

	private SelectionListener createSelectionListener() {
		return new SelectionListener() {
			private static final long serialVersionUID = -1283445467720564426L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Long taskId = ((TaskDetailsButton) arg0.getSource()).getTaskId();
				CTabItem cTabItem = null;
				
				//Checking if the tab is already open
				for (CTabItem c : projectTabFolder.getItems()) {
					if (c.getControl().getClass() == TaskDetails.class) {
						if (((TaskDetails)c.getControl()).getTaskId() == taskId) {
							cTabItem = c;
							projectTabFolder.setSelection(c);
						}
					}
				}
				
				if (cTabItem == null) {
					cTabItem = new CTabItem (projectTabFolder, SWT.CLOSE);
					cTabItem.setText ("Task " + taskId.toString());
					cTabItem.setControl(new TaskDetails(projectTabFolder, taskId));
					projectTabFolder.setSelection(cTabItem);	
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		};
	}

	protected Long getTaskId() {
		return taskId;
	}

}
