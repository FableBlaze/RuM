package ee.ut.cs.rum.plugins.configuration.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.google.gson.JsonObject;

import ee.ut.cs.rum.plugins.configuration.internal.Activator;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationUi;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterObjectSelection;

public class ConfigurationItemObjectSelection extends Composite implements ConfigurationItemInterface {
	private static final long serialVersionUID = -4335268682134875034L;
	
	private String internalName;
	private String displayName;
	private boolean required;
	
	public ConfigurationItemObjectSelection(PluginConfigurationUi pluginConfigurationUi, PluginParameterObjectSelection pluginParameterObjectSelection) {
		super(pluginConfigurationUi, SWT.BORDER);
		
		this.internalName=pluginParameterObjectSelection.getInternalName();
		this.displayName=pluginParameterObjectSelection.getDisplayName();
		this.setToolTipText(pluginParameterObjectSelection.getDescription());
		this.required=pluginParameterObjectSelection.getRequired();
		
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
	
	public void addObjectReference(JsonObject inputObjectInstance) {
		Activator.getLogger().info("addObject: " + inputObjectInstance.toString());
	}
	
	public void removeObjectReference (int id)  {
		Activator.getLogger().info("removeObject: " + id);
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
