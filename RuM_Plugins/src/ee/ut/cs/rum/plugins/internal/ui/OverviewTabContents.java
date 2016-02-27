package ee.ut.cs.rum.plugins.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class OverviewTabContents extends Composite {
	private static final long serialVersionUID = 5073868530789359506L;
	
	private PluginsOverview pluginsOverview;
	private PluginsTableViewer pluginsTableViewer;

	public OverviewTabContents(PluginsManagementUI pluginsManagementUI) {
		super(pluginsManagementUI, SWT.NONE);
		this.setLayout(new GridLayout(2, false));
		
		this.pluginsOverview = new PluginsOverview(this, pluginsManagementUI.getPlugins());
		this.pluginsTableViewer = new PluginsTableViewer(this, pluginsManagementUI, pluginsManagementUI.getPlugins());
	}
	
	public PluginsOverview getPluginsOverview() {
		return pluginsOverview;
	}
	
	public PluginsTableViewer getPluginsTableViewer() {
		return pluginsTableViewer;
	}
	
}
