package ee.ut.cs.rum.workspace.internal.ui.task.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;

public class TaskSubTaskInfo extends Composite {
	private static final long serialVersionUID = -5261425012881560444L;

	public TaskSubTaskInfo(TaskDetailsContainer taskDetailsContainer, RumController rumController) {
		super(taskDetailsContainer, SWT.NONE);
		
		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Sub-task info (TODO)");
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}
}
