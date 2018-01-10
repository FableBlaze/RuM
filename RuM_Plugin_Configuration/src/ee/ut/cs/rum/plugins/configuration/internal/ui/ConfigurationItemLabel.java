package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationUi;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterLabel;

public class ConfigurationItemLabel extends Label implements ConfigurationItemInterface {
	private static final long serialVersionUID = -1455517810144349011L;

	private String displayName;

	public ConfigurationItemLabel(PluginConfigurationUi pluginConfigurationUi, PluginParameterLabel pluginParameterLabel) {
		super(pluginConfigurationUi, SWT.NONE);

		this.displayName=pluginParameterLabel.getDisplayName();
		this.setToolTipText(pluginParameterLabel.getDescription());
		this.setText(pluginParameterLabel.getDisplayName());
		this.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false));
	}

	@Override
	public void setValue(String value) {
	}

	@Override
	public String getValue() {
		//This should never be called for label
		return null;
	}

	@Override
	public String getInternalName() {
		//This should never be called for label
		return null;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public boolean getRequired() {
		//This should never be called for label
		return false;
	}
}
