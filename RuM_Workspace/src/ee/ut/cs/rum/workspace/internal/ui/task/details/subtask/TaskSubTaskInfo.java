package ee.ut.cs.rum.workspace.internal.ui.task.details.subtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.workspace.internal.ui.task.details.TaskDetailsContainer;

public class TaskSubTaskInfo extends Composite {
	private static final long serialVersionUID = -5261425012881560444L;

	public TaskSubTaskInfo(TaskDetailsContainer taskDetailsContainer, RumController rumController, SubTask subTask) {
		super(taskDetailsContainer, SWT.NONE);
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		new TaskSubTaskInfoLeft(this, rumController, subTask);
		
		new TaskSubTaskInfoRight(this, subTask);
	}
}
