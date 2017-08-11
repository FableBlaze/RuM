package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Spinner;
import ee.ut.cs.rum.plugins.configuration.internal.Activator;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterInteger;

public class ConfigurationItemInteger extends Spinner implements ConfigurationItemInterface {
	private static final long serialVersionUID = -7798869134328255376L;

	private String internalName;
	private String displayName;
	private boolean required;
	
	public ConfigurationItemInteger(PluginConfigurationComposite pluginConfigurationComposite, PluginParameterInteger parameterInteger) {
		super(pluginConfigurationComposite, SWT.BORDER);
		
		this.internalName=parameterInteger.getInternalName();
		this.displayName=parameterInteger.getDisplayName();
		this.setToolTipText(parameterInteger.getDescription());
		this.required=parameterInteger.getRequired();
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.setSelection(parameterInteger.getDefaultValue());
		if (parameterInteger.getMaxValue()!=null) {
			this.setMaximum(parameterInteger.getMaxValue());			
		}
		if (parameterInteger.getMinValue()!=null) {
			this.setMinimum(parameterInteger.getMinValue());			
		}
	}

	@Override
	public void setValue(String value) {
		try {	
			this.setSelection(Integer.parseInt(value));
		} catch(NumberFormatException ex) {
			if (!value.equals("")) {
				Activator.getLogger().info(this.getClass().getSimpleName() + " can not be set to: " + value);				
			}
		}
	}

	@Override
	public String getValue() {
		return Integer.toString(this.getSelection());
	}

	@Override
	public String getInternalName() {
		return internalName;
	}
	
	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public boolean getRequired() {
		return required;
	}
}
