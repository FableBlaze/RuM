package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterDouble;

public class ConfigurationItemDouble extends Text implements ConfigurationItemInterface {
	private static final long serialVersionUID = -6806863065839357763L;

	private String internalName;
	
	public ConfigurationItemDouble(PluginConfigurationComposite pluginConfigurationComposite, PluginParameterDouble parameterDouble) {
		super(pluginConfigurationComposite, SWT.BORDER);
		
		this.internalName=parameterDouble.getInternalName();
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.setText(Double.toString(parameterDouble.getDefaultValue()));
		this.setToolTipText(parameterDouble.getDescription());
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
