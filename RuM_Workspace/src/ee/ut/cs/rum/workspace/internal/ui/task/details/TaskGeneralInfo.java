package ee.ut.cs.rum.workspace.internal.ui.task.details;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;

public class TaskGeneralInfo extends Composite {
	private static final long serialVersionUID = 684141659312417380L;

	public TaskGeneralInfo(TaskDetailsContainer taskDetailsContainer, RumController rumController) {
		super(taskDetailsContainer, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3, false));
		
		Task task = taskDetailsContainer.getTaskDetailsComposite().getTask();
		
		Label label;
		
		label = new Label(this, SWT.NONE);
		label.setText("Task name:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText(task.getName());
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
				
		Label taskOutputsTable = new Label(this, SWT.NONE);
		taskOutputsTable.setText("Task output files (TODO)");
		taskOutputsTable.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		((GridData) taskOutputsTable.getLayoutData()).verticalSpan = 6;
		
		label = new Label(this, SWT.NONE);
		label.setText("Task description:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText(task.getDescription());
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Sub-tasks:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText("TODO");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Task status:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText(task.getStatus().toString());
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Created at:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(task.getCreatedAt()));
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Last change at:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(task.getLastModifiedAt()));
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		Label subTaskGraphComposite = new Label(this, SWT.NONE);
		subTaskGraphComposite.setText("Sub-task graph (TODO)");
		subTaskGraphComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		((GridData) subTaskGraphComposite.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
	}
}
