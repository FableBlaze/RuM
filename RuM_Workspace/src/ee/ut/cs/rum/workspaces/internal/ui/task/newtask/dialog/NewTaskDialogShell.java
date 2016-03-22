package ee.ut.cs.rum.workspaces.internal.ui.task.newtask.dialog;

import org.eclipse.swt.widgets.Shell;

import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginConfiguration;

public class NewTaskDialogShell extends Shell {
	private static final long serialVersionUID = -4970825745896119968L;
	
	NewTaskDialog newTaskDialog;
	private RumPluginConfiguration rumPluginConfiguration;

	public NewTaskDialogShell(Shell parent, int style, NewTaskDialog newTaskDialog) {
		super(parent, style);
		
		this.newTaskDialog=newTaskDialog;
	}

	public RumPluginConfiguration getRumPluginConfiguration() {
		return rumPluginConfiguration;
	}

	public void setRumPluginConfiguration(RumPluginConfiguration rumPluginConfiguration) {
		this.rumPluginConfiguration = rumPluginConfiguration;
	}
	
	public NewTaskDialog getNewTaskDialog() {
		return newTaskDialog;
	}
}
