package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterInteger;

public class ConfigurationItemInteger extends Text implements ConfigurationItemInterface {
	private static final long serialVersionUID = -7798869134328255376L;

	public ConfigurationItemInteger(Composite parent, PluginParameterInteger parameterInteger) {
		super(parent, SWT.BORDER);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.setText(Integer.toString(parameterInteger.getDefaultValue()));
		this.setToolTipText(parameterInteger.getDescription());
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
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}

}
