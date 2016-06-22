package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.pluginstable.PluginsTableComposite;

public class NewTaskSubTaskInfo extends Composite {
	private static final long serialVersionUID = -9081862727975335668L;
	
	private Text subTaskNameText;
	private Text subTaskDescriptionText;

	public NewTaskSubTaskInfo(NewTaskDetailsContainer newTaskDetailsContainer, RumController rumController) {
		super(newTaskDetailsContainer, SWT.NONE);
		
		this.setLayout(new GridLayout(4, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		PluginsTableComposite pluginsTableComposite = new PluginsTableComposite(this, rumController);
		((GridData) pluginsTableComposite.getLayoutData()).verticalSpan=3;
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Sub-task name:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		subTaskNameText = new Text(this, SWT.BORDER);
		subTaskNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Selected plugin info (TODO)");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		((GridData) label.getLayoutData()).verticalSpan=2;
		
		label = new Label(this, SWT.NONE);
		label.setText("Sub-task description:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));
		subTaskDescriptionText = new Text(this, SWT.BORDER);
		subTaskDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Plugin configuration UI (TODO)");
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		((GridData) label.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns-1;
		
	}
	
	public String getSubTaskNameText() {
		return subTaskNameText.getText();
	}
	
	public String getSubTaskDescription() {
		return subTaskDescriptionText.getText();
	}
}
