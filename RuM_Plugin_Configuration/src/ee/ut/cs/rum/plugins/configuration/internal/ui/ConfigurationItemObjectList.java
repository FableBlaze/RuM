package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationUi;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterObjectList;

public class ConfigurationItemObjectList extends Composite implements ConfigurationItemInterface {
	private static final long serialVersionUID = -6538283686156068894L;
	
	private String internalName;
	private String displayName;
	private boolean required;

	public ConfigurationItemObjectList(PluginConfigurationUi pluginConfigurationUi, PluginParameterObjectList parameterObjectList) {
		super(pluginConfigurationUi, SWT.BORDER);
		
		this.internalName=parameterObjectList.getInternalName();
		this.displayName=parameterObjectList.getDisplayName();
		this.setToolTipText(parameterObjectList.getDescription());
		this.required=parameterObjectList.getRequired();
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return "TODO";
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
