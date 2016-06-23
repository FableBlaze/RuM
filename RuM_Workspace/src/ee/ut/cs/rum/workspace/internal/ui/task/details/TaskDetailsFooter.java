package ee.ut.cs.rum.workspace.internal.ui.task.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class TaskDetailsFooter extends Composite {
	private static final long serialVersionUID = 8793166001814894685L;

	public TaskDetailsFooter(TaskDetailsComposite taskDetailsComposite) {
		super(taskDetailsComposite, SWT.NONE);
		
		this.setLayout(new GridLayout(1, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	}
}
