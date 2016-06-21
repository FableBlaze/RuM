package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.pluginstable.PluginsTableComposite;

public class NewTaskSubTaskInfo extends Composite {
	private static final long serialVersionUID = -9081862727975335668L;

	public NewTaskSubTaskInfo(NewTaskDetailsContainer newTaskDetailsContainer, RumController rumController) {
		super(newTaskDetailsContainer, SWT.NONE);
		
		this.setLayout(new GridLayout(4, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		new PluginsTableComposite(this, rumController);
		
		Label l = new Label(this, SWT.NONE);
		l.setText("Sections related to plugin selection (TODO)");
		l.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
	}
}
