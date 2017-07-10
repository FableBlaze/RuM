package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterString;

public class ConfigurationItemString extends Text implements ConfigurationItemInterface {
	private static final long serialVersionUID = 7030044951525201312L;
	
	private String internalName;
	
	public ConfigurationItemString(PluginConfigurationComposite pluginConfigurationComposite, PluginParameterString parameterString) {
		super(pluginConfigurationComposite, SWT.BORDER);
		
		this.internalName=parameterString.getInternalName();
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.setText(parameterString.getDefaultValue());
		this.setToolTipText(parameterString.getDescription());
	}

	@Override
	public void setValue(String value) {
		this.setText(value);
	}

	@Override
	public String getValue() {
		return this.getText();
	}

	@Override
	public String getInternalName() {
		return internalName;
	}
}
