package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Spinner;
import ee.ut.cs.rum.plugins.configuration.internal.Activator;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterDouble;

public class ConfigurationItemDouble extends Spinner implements ConfigurationItemInterface {
	private static final long serialVersionUID = -6806863065839357763L;

	private String internalName;
	private String displayName;
	private boolean required;
	private int decimalPlaces;

	public ConfigurationItemDouble(PluginConfigurationComposite pluginConfigurationComposite, PluginParameterDouble parameterDouble) {
		super(pluginConfigurationComposite, SWT.BORDER);

		this.internalName=parameterDouble.getInternalName();
		this.displayName=parameterDouble.getDisplayName();
		this.setToolTipText(parameterDouble.getDescription());
		this.required=parameterDouble.getRequired();
		this.decimalPlaces=parameterDouble.getDecimalPlaces();

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.setSelection((int) (parameterDouble.getDefaultValue()*Math.pow(10, decimalPlaces)));
		this.setMaximum((int) (parameterDouble.getMaxValue()*Math.pow(10, decimalPlaces)));
		this.setMinimum((int) (parameterDouble.getMinValue()*Math.pow(10, decimalPlaces)));
		this.setDigits(parameterDouble.getDecimalPlaces());
		
	}

	@Override
	public void setValue(String value) {
		try {			
			this.setSelection((int) (Double.parseDouble(value)*Math.pow(10, decimalPlaces)));
		} catch(NumberFormatException ex) {
			Activator.getLogger().info(this.getClass().getSimpleName() + " can not be set to: " + value);
		}
	}

	@Override
	public String getValue() {
		return Double.toString(this.getSelection()/Math.pow(10, decimalPlaces));
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
