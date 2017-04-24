package ee.ut.cs.rum.plugins.internal.ui.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class PluginDetails extends Composite {
	private static final long serialVersionUID = 7587175465086104655L;

	private Plugin plugin;
	
	public PluginDetails(PluginsManagementUI pluginsManagementUI, Plugin plugin, RumController rumController) {
		super(pluginsManagementUI, SWT.NONE);
		
		this.plugin=plugin;

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout());
		
		PluginDetailsContents pluginDetailsContents = new PluginDetailsContents(this, plugin, rumController);
		pluginDetailsContents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		PluginDetailsFooter pluginDetailsFooter = new PluginDetailsFooter(this);
		pluginDetailsFooter.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		
	}
	
	public Plugin getPlugin() {
		return plugin;
	}
	
}
