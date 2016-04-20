package ee.ut.cs.rum.plugins.configuration.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemDouble;
import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemFile;
import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemInteger;
import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemInterface;
import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemSelection;
import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemString;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameter;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterDouble;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterInteger;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterString;

public class PluginConfigurationComposite extends Composite {
	private static final long serialVersionUID = -5475837154117723386L;
	
	private Map<String, ConfigurationItemInterface> configurationItems;
	
	public PluginConfigurationComposite(Composite parent, PluginInfo pluginInfo) {
		super(parent, SWT.NONE);
		this.setLayout(new GridLayout(2, false));
		
		createContents(pluginInfo);
	}

	private void createContents(PluginInfo pluginInfo) {
		configurationItems = new HashMap<String, ConfigurationItemInterface>();
		
		Label label = new Label (this, SWT.NONE);
		label.setText(pluginInfo.getName());
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) label.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;

		label = new Label (this, SWT.NONE);
		label.setText(pluginInfo.getDescription());
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) label.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		
		for (PluginParameter pluginParameter : pluginInfo.getParameters()) {
			
			label = new Label (this, SWT.NONE);
			label.setText(pluginParameter.getDisplayName());
			
			switch (pluginParameter.getParameterType()) {
			case STRING:
				PluginParameterString parameterString = (PluginParameterString) pluginParameter;
				configurationItems.put(parameterString.getInternalName(), new ConfigurationItemString(this, parameterString));
				break; 
			case INTEGER:
				PluginParameterInteger parameterInteger = (PluginParameterInteger) pluginParameter;
				configurationItems.put(parameterInteger.getInternalName(), new ConfigurationItemInteger(this, parameterInteger));
				break; 
			case DOUBLE:
				PluginParameterDouble parameterDouble = (PluginParameterDouble) pluginParameter;
				configurationItems.put(parameterDouble.getInternalName(), new ConfigurationItemDouble(this, parameterDouble));
				break; 
			case SELECTION:
				PluginParameterSelection parameterSelection = (PluginParameterSelection) pluginParameter;
				configurationItems.put(parameterSelection.getInternalName(), new ConfigurationItemSelection(this, parameterSelection));
				break; 
			case FILE:
				PluginParameterFile parameterFile = (PluginParameterFile) pluginParameter;
				configurationItems.put(parameterFile.getInternalName(), new ConfigurationItemFile(this, parameterFile));
				break; 
			default:
				break;
			}
		}
	}
	
	public Map<String, String> getConfigurationValues() {
		Map<String, String> configurationValues = new HashMap<String, String>();
		for (String key : configurationItems.keySet()) {
			if (configurationItems.get(key).getValue()!=null) {
				configurationValues.put(key, configurationItems.get(key).getValue());
			} else {
				configurationValues.put(key, "");
			}
		}
		return configurationValues;
	}
	
	public void setConfigurationValues(Map<String, String> configurationValues) {
		for (String key : configurationValues.keySet()) {
			if (!configurationItems.get(key).equals("")) {
				configurationItems.get(key).setValue(configurationValues.get(key));	
			}
		}
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		for (ConfigurationItemInterface configurationItem : configurationItems.values()) {
			configurationItem.setEnabled(enabled);
		}
	}
	
}
