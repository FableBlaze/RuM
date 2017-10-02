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

public class PluginConfigurationContainer extends ScrolledComposite {
	private static final long serialVersionUID = 1011296086711861676L;

	private PluginConfigurationUi pluginConfigurationUi;

	public PluginConfigurationContainer(Composite parent) {
		super(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	public void showDisabledPluginConfigurationUi(Plugin plugin) {
		disposeCurrentPluginConfigurationUi();

		PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(plugin);
		pluginConfigurationUi = new PluginConfigurationUi(this, pluginInfo);

		this.setContent(pluginConfigurationUi);
		pluginConfigurationUi.setSize(pluginConfigurationUi.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public void showEnabledPluginConfigurationUi(Plugin plugin, RumController rumController, List<UserFile> userFiles, List<UserFile> taskUserFiles, List<UserFile> tmpUserFiles) {
		disposeCurrentPluginConfigurationUi();

		PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(plugin);
		pluginConfigurationUi = new PluginConfigurationUi(this, pluginInfo, rumController, userFiles, taskUserFiles, tmpUserFiles);

		this.setContent(pluginConfigurationUi);
		pluginConfigurationUi.setSize(pluginConfigurationUi.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public void disposeCurrentPluginConfigurationUi() {
		if (pluginConfigurationUi!=null) {
			pluginConfigurationUi.dispose();
		}
	}

	public PluginConfigurationUi getPluginConfigurationUi() {
		return pluginConfigurationUi;
	}
}
