package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class NewTaskGeneralInfo extends Composite {
	private static final long serialVersionUID = 5067024324443453774L;

	public NewTaskGeneralInfo(NewTaskDetailsContainer newTaskDetailsContainer) {
		super(newTaskDetailsContainer, SWT.NONE);
		
		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label l = new Label(this, SWT.NONE);
		l.setText("General info");
	}
}
