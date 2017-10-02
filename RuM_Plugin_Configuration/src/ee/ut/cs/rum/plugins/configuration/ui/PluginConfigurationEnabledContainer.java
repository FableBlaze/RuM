package ee.ut.cs.rum.plugins.configuration.ui;

import java.util.List;

import org.eclipse.swt.SWT;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.plugins.configuration.util.PluginUtils;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;

public class PluginConfigurationEnabledContainer extends PluginConfigurationContainer {
	private static final long serialVersionUID = 2858214530170110953L;
	
	private PluginConfigurationEnabledContainerParent pluginConfigurationEnabledContainerParent;

	public PluginConfigurationEnabledContainer(PluginConfigurationEnabledContainerParent pluginConfigurationEnabledContainerParent) {
		super(pluginConfigurationEnabledContainerParent);
		
		this.pluginConfigurationEnabledContainerParent=pluginConfigurationEnabledContainerParent;
	}
	
	public void showEnabledPluginConfigurationUi(Plugin plugin, RumController rumController, List<UserFile> userFiles, List<UserFile> taskUserFiles, List<UserFile> tmpUserFiles) {
		super.disposeCurrentPluginConfigurationUi();

		PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(plugin);
		super.pluginConfigurationUi = new PluginConfigurationUi(this, pluginInfo, rumController, userFiles, taskUserFiles, tmpUserFiles);

		super.setContent(pluginConfigurationUi);
		pluginConfigurationUi.setSize(pluginConfigurationUi.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public PluginConfigurationEnabledContainerParent getPluginConfigurationEnabledContainerParent() {
		return pluginConfigurationEnabledContainerParent;
	}
}
