package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class NewTaskSubTaskInfo extends Composite {
	private static final long serialVersionUID = -9081862727975335668L;

	public NewTaskSubTaskInfo(NewTaskDetailsContainer newTaskDetailsContainer) {
		super(newTaskDetailsContainer, SWT.NONE);
		
		this.setLayout(new GridLayout(4, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		
		
		Label l = new Label(this, SWT.NONE);
		l.setText("Sub-task info");
	}
}
