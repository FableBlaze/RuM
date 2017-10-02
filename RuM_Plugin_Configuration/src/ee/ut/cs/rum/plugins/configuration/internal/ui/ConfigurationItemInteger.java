package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Spinner;
import ee.ut.cs.rum.plugins.configuration.internal.Activator;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationUi;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterInteger;

public class ConfigurationItemInteger extends Spinner implements ConfigurationItemInterface {
	private static final long serialVersionUID = -7798869134328255376L;

	private String internalName;
	private String displayName;
	private boolean required;
	
	public ConfigurationItemInteger(PluginConfigurationUi pluginConfigurationUi, PluginParameterInteger parameterInteger) {
		super(pluginConfigurationUi, SWT.BORDER);
		
		this.internalName=parameterInteger.getInternalName();
		this.displayName=parameterInteger.getDisplayName();
		this.setToolTipText(parameterInteger.getDescription());
		this.required=parameterInteger.getRequired();
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		//TODO: Negative values issue
		if (parameterInteger.getMinValue()!=null && parameterInteger.getMaxValue()!=null) {
			this.setValues(parameterInteger.getDefaultValue(), parameterInteger.getMinValue(), parameterInteger.getMaxValue(), this.getDigits(), this.getIncrement(), this.getPageIncrement());
		} else if (parameterInteger.getMinValue()!=null) {
			this.setValues(parameterInteger.getDefaultValue(), parameterInteger.getMinValue(), this.getMaximum(), this.getDigits(), this.getIncrement(), this.getPageIncrement());
		} else if (parameterInteger.getMaxValue()!=null) {
			this.setValues(parameterInteger.getDefaultValue(), this.getMinimum(), parameterInteger.getMaxValue(), this.getDigits(), this.getIncrement(), this.getPageIncrement());
		} else {
			this.setValues(parameterInteger.getDefaultValue(), this.getMinimum(), this.getMaximum(), this.getDigits(), this.getIncrement(), this.getPageIncrement());
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
