package ee.ut.cs.rum.workspace.internal.ui.task.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;

public class TaskDetailsContainer extends Composite {
	private static final long serialVersionUID = -8224474950844756812L;

	public TaskDetailsContainer(TaskDetailsComposite taskDetailsComposite, RumController rumController) {
		super(taskDetailsComposite, SWT.NONE);
	}
}
