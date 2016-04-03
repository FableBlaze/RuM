package ee.ut.cs.rum.plugins.development.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameter;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterDouble;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterInteger;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterString;
import ee.ut.cs.rum.plugins.development.ui.input.ConfigurationItemDouble;
import ee.ut.cs.rum.plugins.development.ui.input.ConfigurationItemFile;
import ee.ut.cs.rum.plugins.development.ui.input.ConfigurationItemInteger;
import ee.ut.cs.rum.plugins.development.ui.input.ConfigurationItemInterface;
import ee.ut.cs.rum.plugins.development.ui.input.ConfigurationItemSelection;
import ee.ut.cs.rum.plugins.development.ui.input.ConfigurationItemString;

public class PluginConfigurationUi extends Composite {
	private static final long serialVersionUID = -5475837154117723386L;
	
	private Map<String, ConfigurationItemInterface> configurationItems;
	
	public PluginConfigurationUi(Composite parent, PluginInfo pluginInfo) {
		super(parent, SWT.CLOSE | SWT.H_SCROLL | SWT.V_SCROLL);
		
		this.setLayout(new GridLayout(2, false));
		
		createContents(pluginInfo);
		
		this.setSize(this.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
			configurationValues.put(key, configurationItems.get(key).getValue());
		}
		
		return configurationValues;
		
	}
	
	public void setConfigurationValues(Map<String, String> configurationValues) {
		for (String key : configurationValues.keySet()) {
			configurationItems.get(key).setValue(configurationValues.get(key));
		}
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		for (ConfigurationItemInterface configurationItem : configurationItems.values()) {
			configurationItem.setEnabled(enabled);
		}
	}
	
	
}
