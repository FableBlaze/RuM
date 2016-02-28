package ee.ut.cs.rum.plugins.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.util.PluginsData;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class PluginDetails extends Composite {
	private static final long serialVersionUID = 458942786595146853L;
	
	private Long pluginId;
	
	public PluginDetails(PluginsManagementUI pluginsManagementUI, Long pluginId) {
		super(pluginsManagementUI, SWT.CLOSE);
		this.setLayout(new GridLayout(2, false));
		this.pluginId = pluginId;
		
		Plugin plugin = PluginsData.getPluginDataFromDb(pluginId);
		
		createContents(plugin);
	}
	
	private void createContents(Plugin plugin) {
		Label label = new Label (this, SWT.NONE);
		label.setText("Plugin Id:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getId().toString());
		
		label = new Label (this, SWT.NONE);
		label.setText("Plugin Name:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getName());
		
		label = new Label (this, SWT.NONE);
		label.setText("Plugin Description:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getDescription());
	}
	
	public Long getPluginId() {
		return pluginId;
	}

}
