package ee.ut.cs.rum.workspace.internal.ui.task.details.sidebar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.task.details.TaskDetailsComposite;

public class DetailsSidebar extends Composite {
	private static final long serialVersionUID = -4976100121238756769L;

	public DetailsSidebar(TaskDetailsComposite taskDetailsComposite, RumController rumController) {
		super(taskDetailsComposite, SWT.NONE);
		
		this.setLayout(new GridLayout(1, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
	}
}
