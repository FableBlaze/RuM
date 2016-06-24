package ee.ut.cs.rum.workspace.internal.ui.task.details.subtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;

public class TaskSubTaskInfoRight extends Composite {
	private static final long serialVersionUID = 2108550824294466913L;

	public TaskSubTaskInfoRight(TaskSubTaskInfo taskSubTaskInfo, RumController rumController, SubTask subTask) {
		super(taskSubTaskInfo, SWT.NONE);
		
		this.setLayout(new GridLayout(1, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label pluginConfiguration = new Label(this, SWT.NONE);
		pluginConfiguration.setText("Plugin configuration (TODO)");
		pluginConfiguration.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		
		Label taskOutputFiles = new Label(this, SWT.NONE);
		taskOutputFiles.setText("Task output files (TODO)");
		taskOutputFiles.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
	}

}
