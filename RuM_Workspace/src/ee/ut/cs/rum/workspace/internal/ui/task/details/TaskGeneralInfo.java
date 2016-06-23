package ee.ut.cs.rum.workspace.internal.ui.task.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;

public class TaskGeneralInfo extends Composite {
	private static final long serialVersionUID = 684141659312417380L;

	public TaskGeneralInfo(TaskDetailsContainer taskDetailsContainer, RumController rumController) {
		super(taskDetailsContainer, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3, false));
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Task general info (TODO)");
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}
}
