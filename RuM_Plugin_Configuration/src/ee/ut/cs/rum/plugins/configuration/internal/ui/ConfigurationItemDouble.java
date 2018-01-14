package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import ee.ut.cs.rum.plugins.configuration.internal.Activator;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterDouble;

public class ConfigurationItemDouble extends Spinner implements ConfigurationItemInterface {
	private static final long serialVersionUID = -6806863065839357763L;

	private String internalName;
	private String displayName;
	private boolean required;

	public ConfigurationItemDouble(Composite parentComposite, PluginParameterDouble parameterDouble) {
		super(parentComposite, SWT.BORDER);

		this.internalName=parameterDouble.getInternalName();
		this.displayName=parameterDouble.getDisplayName();
		this.setToolTipText(parameterDouble.getDescription());
		this.required=parameterDouble.getRequired();

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.setDigits(parameterDouble.getDecimalPlaces());
		if (parameterDouble.getMinValue()!=null && parameterDouble.getMaxValue()!=null) {
			this.setValues(doubleToSpinnerInt(parameterDouble.getDefaultValue()), doubleToSpinnerInt(parameterDouble.getMinValue()), doubleToSpinnerInt(parameterDouble.getMaxValue()), this.getDigits(), this.getIncrement(), this.getPageIncrement());
		} else if (parameterDouble.getMinValue()!=null) {
			this.setValues(doubleToSpinnerInt(parameterDouble.getDefaultValue()), doubleToSpinnerInt(parameterDouble.getMinValue()), this.getMaximum(), this.getDigits(), this.getIncrement(), this.getPageIncrement());
		} else if (parameterDouble.getMaxValue()!=null) {
			this.setValues(doubleToSpinnerInt(parameterDouble.getDefaultValue()), this.getMinimum(), doubleToSpinnerInt(parameterDouble.getMaxValue()), this.getDigits(), this.getIncrement(), this.getPageIncrement());
		} else {
			this.setValues(doubleToSpinnerInt(parameterDouble.getDefaultValue()), this.getMinimum(), this.getMaximum(), this.getDigits(), this.getIncrement(), this.getPageIncrement());
		}
	}
	
	private int doubleToSpinnerInt(Double doubleValue) {
		return (int) (doubleValue*Math.pow(10, this.getDigits()));
	}
	

	@Override
	public void setValue(String value) {
		try {
			this.setSelection(doubleToSpinnerInt(Double.parseDouble(value)));
		} catch(NumberFormatException ex) {
			Activator.getLogger().info(this.getClass().getSimpleName() + " can not be set to: " + value);
		}
	}

	@Override
	public String getValue() {
		return Double.toString(this.getSelection()/Math.pow(10, this.getDigits()));
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
