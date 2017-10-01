package ee.ut.cs.rum.plugins.configuration.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.configuration.util.PluginUtils;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;

public class ScrolledPluginConfigurationComposite extends ScrolledComposite {
	private static final long serialVersionUID = 1011296086711861676L;
	
	private PluginConfigurationComposite pluginConfigurationComposite;

	public ScrolledPluginConfigurationComposite(Composite parent) {
		super(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}
	
	public void showDisabledPluginConfigurationComposite(Plugin plugin) {
		if (pluginConfigurationComposite!=null) {
			pluginConfigurationComposite.dispose();
		}
		
		PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(plugin);
		pluginConfigurationComposite = new PluginConfigurationComposite(this, pluginInfo);

		this.setContent(pluginConfigurationComposite);
		pluginConfigurationComposite.setSize(pluginConfigurationComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	public PluginConfigurationComposite getPluginConfigurationComposite() {
		return pluginConfigurationComposite;
	}
}
