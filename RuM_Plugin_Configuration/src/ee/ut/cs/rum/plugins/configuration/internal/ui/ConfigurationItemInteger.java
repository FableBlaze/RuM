package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.plugins.configuration.internal.Activator;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterInteger;

public class ConfigurationItemInteger extends Text implements ConfigurationItemInterface {
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
		
		this.setText(Integer.toString(parameterInteger.getDefaultValue()));
		this.addVerifyListener(new VerifyListener() {
			private static final long serialVersionUID = -316837755145839293L;
			//TODO: Consider using Spinner
			//TODO: Feedback to user
			//TODO: Set min and max value listeners

			@Override
			public void verifyText(VerifyEvent event) {
				try {
					Integer.parseInt(event.text);
				} catch(NumberFormatException ex) {
					if (!event.text.equals("")) {
						event.doit=false;
					}
				}
			}
		});
	}

	@Override
	public void setValue(String value) {
		try {
			Integer.parseInt(value);			
			this.setText(value);
		} catch(NumberFormatException ex) {
			if (!value.equals("")) {
				Activator.getLogger().info(this.getClass().getSimpleName() + " can not be set to: " + value);				
			}
			this.setText("");
		}
	}

	@Override
	public String getValue() {
		return this.getText();
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
