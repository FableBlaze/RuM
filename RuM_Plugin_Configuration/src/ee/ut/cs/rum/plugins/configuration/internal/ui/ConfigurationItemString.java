package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationUi;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterString;

public class ConfigurationItemString extends Text implements ConfigurationItemInterface {
	private static final long serialVersionUID = 7030044951525201312L;
	
	private String internalName;
	private String displayName;
	private boolean required;
	
	public ConfigurationItemString(PluginConfigurationUi pluginConfigurationUi, PluginParameterString parameterString) {
		super(pluginConfigurationUi, SWT.BORDER);
		
		this.internalName=parameterString.getInternalName();
		this.displayName=parameterString.getDisplayName();
		this.setToolTipText(parameterString.getDescription());
		this.required=parameterString.getRequired();
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		if (parameterString.getDefaultValue()!=null) {
			this.setText(parameterString.getDefaultValue());			
		}
		
		if (parameterString.getMaxInputLength()!= null) {
			this.setTextLimit(parameterString.getMaxInputLength());			
		}
		
		if (parameterString.getAllowedCharacters()!=null) {
			this.addVerifyListener(new VerifyListener() {
				private static final long serialVersionUID = -988037016675261725L;

				@Override
				public void verifyText(VerifyEvent event) {
					for (int i = 0; i < event.text.length(); i++) {
						//Have to check the entire string because event.character is empty for some reason
						if (parameterString.getAllowedCharacters().indexOf((event.text.charAt(i)))==-1) {
							event.doit=false;
							break;
						}
					}
				}
			});
		}
		
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
	
	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public boolean getRequired() {
		return required;
	}
	
}
