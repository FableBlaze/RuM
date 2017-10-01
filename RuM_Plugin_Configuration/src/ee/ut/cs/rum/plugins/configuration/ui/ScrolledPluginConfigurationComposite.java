package ee.ut.cs.rum.plugins.configuration.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.UserFile;
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
		disposeCurrentPluginConfigurationComposite();

		PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(plugin);
		pluginConfigurationComposite = new PluginConfigurationComposite(this, pluginInfo);

		this.setContent(pluginConfigurationComposite);
		pluginConfigurationComposite.setSize(pluginConfigurationComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public void showEnabledPluginConfigurationComposite(Plugin plugin, RumController rumController, List<UserFile> userFiles, List<UserFile> taskUserFiles, List<UserFile> tmpUserFiles) {
		disposeCurrentPluginConfigurationComposite();

		PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(plugin);
		pluginConfigurationComposite = new PluginConfigurationComposite(this, pluginInfo, rumController, userFiles, taskUserFiles, tmpUserFiles);

		this.setContent(pluginConfigurationComposite);
		pluginConfigurationComposite.setSize(pluginConfigurationComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public void disposeCurrentPluginConfigurationComposite() {
		if (pluginConfigurationComposite!=null) {
			pluginConfigurationComposite.dispose();
		}
	}

	public PluginConfigurationComposite getPluginConfigurationComposite() {
		return pluginConfigurationComposite;
	}
}
