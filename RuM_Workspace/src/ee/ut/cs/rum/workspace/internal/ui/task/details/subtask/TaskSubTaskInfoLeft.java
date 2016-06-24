package ee.ut.cs.rum.workspace.internal.ui.task.details.subtask;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.workspace.internal.ui.task.PluginInfoComposite;

public class TaskSubTaskInfoLeft extends Composite {
	private static final long serialVersionUID = -3780211750244322039L;

	public TaskSubTaskInfoLeft(TaskSubTaskInfo taskSubTaskInfo, RumController rumController, SubTask subTask) {
		super(taskSubTaskInfo, SWT.NONE);

		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

		Label label;
		
		label = new Label(this, SWT.NONE);
		label.setText("Sub-task Info");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false));
		((GridData) label.getLayoutData()).horizontalSpan = ((GridLayout)this.getLayout()).numColumns;

		label = new Label(this, SWT.NONE);
		label.setText("Sub-task name:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText(subTask.getName());
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Sub-task decription:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText(subTask.getDescription());
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Sub-task status:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText(subTask.getStatus().toString());
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Last change at:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText("TODO");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Created at:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(subTask.getCreatedAt()));
		label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
		
		PluginInfoComposite pluginInfoComposite = new PluginInfoComposite(this);
		pluginInfoComposite.updateSelectedPluginInfo(subTask.getPlugin());
		pluginInfoComposite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
		((GridData) pluginInfoComposite.getLayoutData()).horizontalSpan = ((GridLayout)this.getLayout()).numColumns;
	}
}
