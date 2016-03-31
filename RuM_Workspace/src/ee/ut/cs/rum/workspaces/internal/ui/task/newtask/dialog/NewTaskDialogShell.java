package ee.ut.cs.rum.workspaces.internal.ui.task.newtask.dialog;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class NewTaskDialogShell extends Shell {
	private static final long serialVersionUID = -4970825745896119968L;
	
	NewTaskDialog newTaskDialog;
	private Label rumPluginConfiguration;

	public NewTaskDialogShell(Shell parent, int style, NewTaskDialog newTaskDialog) {
		super(parent, style);
		
		this.newTaskDialog=newTaskDialog;
	}

	public Label getRumPluginConfiguration() {
		return rumPluginConfiguration;
	}

	public void setRumPluginConfiguration(Label rumPluginConfiguration) {
		this.pack();
		this.rumPluginConfiguration = rumPluginConfiguration;
	}
	
	public NewTaskDialog getNewTaskDialog() {
		return newTaskDialog;
	}
}
