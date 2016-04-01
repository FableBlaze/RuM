package ee.ut.cs.rum.workspaces.internal.ui.task.newtask.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

import ee.ut.cs.rum.workspaces.internal.ui.task.newtask.pluginstable.PluginsTableComposite;
import ee.ut.cs.rum.workspaces.internal.ui.workspace.WorkspaceDetailsTabContents;
import ee.ut.cs.rum.workspaces.internal.ui.workspace.WorkspaceTabFolder;

public class NewTaskDialog extends Dialog {
	private static final long serialVersionUID = -6828414895866044855L;
	
	private WorkspaceDetailsTabContents workspaceDetails;
	private SelectedPluginInfo selectedPluginInfo;
	private ScrolledComposite selectedPluginConfigurationUi;
	private FooterButtonsComposite footerButtonsComposite;
	private PluginsTableComposite pluginsTableComposite;

	public NewTaskDialog(Shell activeShell, WorkspaceDetailsTabContents workspaceDetails) {
		super(activeShell, SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER);
		
		this.workspaceDetails=workspaceDetails;
	}
	
	public String open() {
		NewTaskDialogShell newTaskDialogShell = new NewTaskDialogShell(getParent(), getStyle(), this);
		newTaskDialogShell.setText("Add new task");
		createContents(newTaskDialogShell);
		newTaskDialogShell.pack();
		newTaskDialogShell.setLocation (100, 100);
		newTaskDialogShell.open();
		return null;
	}
	
	private void createContents(final NewTaskDialogShell newTaskDialogShell) {
		newTaskDialogShell.setLayout(new GridLayout(3, false));
		pluginsTableComposite = new PluginsTableComposite(newTaskDialogShell, this);
		selectedPluginInfo = new SelectedPluginInfo(newTaskDialogShell);
		
		selectedPluginConfigurationUi = new ScrolledComposite(newTaskDialogShell, SWT.H_SCROLL | SWT.V_SCROLL);
		selectedPluginConfigurationUi.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		footerButtonsComposite = new FooterButtonsComposite(newTaskDialogShell);
		((GridData) footerButtonsComposite.getLayoutData()).horizontalSpan=((GridLayout) newTaskDialogShell.getLayout()).numColumns;
	}
	
	public SelectedPluginInfo getSelectedPluginInfo() {
		return selectedPluginInfo;
	}

	public ScrolledComposite getSelectedPluginConfigurationUi() {
		return selectedPluginConfigurationUi;
	}
	
	public FooterButtonsComposite getFooterButtonsComposite() {
		return footerButtonsComposite;
	}
	
	public PluginsTableComposite getPluginsTableComposite() {
		return pluginsTableComposite;
	}
	
	public WorkspaceDetailsTabContents getWorkspaceDetails() {
		return workspaceDetails;
	}
}
