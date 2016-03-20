package ee.ut.cs.rum.workspaces.ui.internal.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginConfiguration;
import ee.ut.cs.rum.workspaces.ui.WorkspacesUI;
import ee.ut.cs.rum.workspaces.ui.internal.newtask.pluginstable.PluginsTableComposite;

public class NewTaskComposite extends Composite {
	private static final long serialVersionUID = -6828414895866044855L;
	
	private SelectedPluginInfo selectedPluginInfo;
	private ScrolledComposite selectedPluginConfigurationUi;
	RumPluginConfiguration rumPluginConfiguration;

	public NewTaskComposite(WorkspacesUI workspaceUI) {
		super(workspaceUI, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3, false));
		
		new PluginsTableComposite(this);
		
		selectedPluginInfo = new SelectedPluginInfo(this);
		
		selectedPluginConfigurationUi = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		selectedPluginConfigurationUi.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		FooterButtonsComposite footerButtonsComposite = new FooterButtonsComposite(this);
		((GridData) footerButtonsComposite.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
		
	}
	
	public SelectedPluginInfo getSelectedPluginInfo() {
		return selectedPluginInfo;
	}

	public ScrolledComposite getSelectedPluginConfigurationUi() {
		return selectedPluginConfigurationUi;
	}

	public RumPluginConfiguration getRumPluginConfiguration() {
		return rumPluginConfiguration;
	}

	public void setRumPluginConfiguration(RumPluginConfiguration rumPluginConfiguration) {
		this.rumPluginConfiguration = rumPluginConfiguration;
	}
}
