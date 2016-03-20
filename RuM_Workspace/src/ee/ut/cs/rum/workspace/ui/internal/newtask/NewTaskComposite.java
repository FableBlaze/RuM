package ee.ut.cs.rum.workspace.ui.internal.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.workspace.ui.WorkspaceUI;
import ee.ut.cs.rum.workspace.ui.internal.newtask.pluginstable.PluginsTableComposite;

public class NewTaskComposite extends Composite {
	private static final long serialVersionUID = -6828414895866044855L;
	
	private SelectedPluginInfo selectedPluginInfo;
	private Composite selectedPluginConfigurationUi;

	public NewTaskComposite(WorkspaceUI workspaceUI) {
		super(workspaceUI, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3, false));
		
		new PluginsTableComposite(this);
		
		selectedPluginInfo = new SelectedPluginInfo(this);
		
		ScrolledComposite sc = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		selectedPluginConfigurationUi = new Composite(sc, SWT.NONE);
		selectedPluginConfigurationUi.setLayout(new GridLayout());
		selectedPluginConfigurationUi.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		sc.setContent(selectedPluginConfigurationUi);
		
		FooterButtonsComposite footerButtonsComposite = new FooterButtonsComposite(this);
		((GridData) footerButtonsComposite.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
		
	}
	
	public SelectedPluginInfo getSelectedPluginInfo() {
		return selectedPluginInfo;
	}

	public Composite getSelectedPluginConfigurationUi() {
		return selectedPluginConfigurationUi;
	}
}
