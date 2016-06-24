package ee.ut.cs.rum.workspace.internal.ui.task.details.sidebar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.task.details.TaskDetailsComposite;

public class TaskDetailsSidebar extends Composite {
	private static final long serialVersionUID = -4976100121238756769L;
	
	private TaskDetailsComposite taskDetailsComposite;
	private SubTaskTableViewer subTaskTableViewer;

	public TaskDetailsSidebar(TaskDetailsComposite taskDetailsComposite, RumController rumController) {
		super(taskDetailsComposite, SWT.NONE);
		
		this.taskDetailsComposite=taskDetailsComposite;
		
		this.setLayout(new GridLayout(1, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Sub-tasks:");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		
		Button generalInfoButton = new Button(this, SWT.PUSH);
		generalInfoButton.setText("General info");
		generalInfoButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		generalInfoButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -9188529455494099314L;

			@Override
			public void handleEvent(Event arg0) {
				taskDetailsComposite.getTaskDetailsContainer().showGeneralInfo();
				subTaskTableViewer.getTable().deselectAll();
			}
		});
		
		this.subTaskTableViewer = new SubTaskTableViewer(this, rumController);
		subTaskTableViewer.addSelectionChangedListener(new SubTaskSelectionChangedListener(taskDetailsComposite));
	}
	
	public SubTaskTableViewer getSubTaskTableViewer() {
		return subTaskTableViewer;
	}
	public TaskDetailsComposite getTaskDetailsComposite() {
		return taskDetailsComposite;
	}
}
