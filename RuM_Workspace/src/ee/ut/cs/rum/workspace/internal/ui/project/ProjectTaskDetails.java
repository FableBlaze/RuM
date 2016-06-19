package ee.ut.cs.rum.workspace.internal.ui.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;

public class ProjectTaskDetails extends Composite {
	private static final long serialVersionUID = 6334776047604763250L;
	
	private final Task task;

	public ProjectTaskDetails(ProjectDetailsContainer projectDetailsContainer, Task task, RumController rumController) {
		super(projectDetailsContainer, SWT.NONE);



		this.task=task;

		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2, false));

		
		
		Label l = new Label(this, SWT.NONE);
		l.setText(task.toString());

	}

}
