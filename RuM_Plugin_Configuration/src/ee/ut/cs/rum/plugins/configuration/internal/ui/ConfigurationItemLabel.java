package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationUi;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterLabel;

public class ConfigurationItemLabel extends Composite implements ConfigurationItemInterface {
	private static final long serialVersionUID = -1455517810144349011L;
	
	private String displayName;
	private boolean required;

	public ConfigurationItemLabel(PluginConfigurationUi pluginConfigurationUi, PluginParameterLabel pluginParameterLabel) {
		super(pluginConfigurationUi, SWT.BORDER);

		this.displayName=pluginParameterLabel.getDisplayName();
		this.setToolTipText(pluginParameterLabel.getDescription());
		this.required=pluginParameterLabel.getRequired();
		
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		createContents();
	}
	
	private void createContents() {
		Label l = new Label(this, SWT.NONE);
		l.setText("TODO");
		l.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
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
		return required;
	}
}
