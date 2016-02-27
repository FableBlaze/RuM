package ee.ut.cs.rum.plugins.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class OverviewTabContents extends Composite {
	private static final long serialVersionUID = 5073868530789359506L;

	public OverviewTabContents(PluginsManagementUI pluginsManagementUI) {
		super(pluginsManagementUI, SWT.NONE);
		this.setLayout(new GridLayout(2, false));
		
		PluginsOverview.createPluginsOverview(this, pluginsManagementUI.getPlugins());
		PluginsTable.createPluginsTable(this, pluginsManagementUI, pluginsManagementUI.getPlugins());
	}
	
}
