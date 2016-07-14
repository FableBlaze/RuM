package ee.ut.cs.rum.plugins.configuration.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemDouble;
import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemFile;
import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemInteger;
import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemInterface;
import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemSelection;
import ee.ut.cs.rum.plugins.configuration.internal.ui.ConfigurationItemString;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.PluginOutput;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameter;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterDouble;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterInteger;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterString;

public class PluginConfigurationComposite extends Composite {
	private static final long serialVersionUID = -5475837154117723386L;
	
	private RumController rumController;

	private Map<String, ConfigurationItemInterface> configurationItems;
	private PluginInfo pluginInfo;
	private List<UserFile> userFiles;
	
	public PluginConfigurationComposite(Composite parent, PluginInfo pluginInfo, RumController rumController, List<UserFile> userFiles) {
		super(parent, SWT.NONE);

		this.rumController=rumController;
		
		this.pluginInfo=pluginInfo;
		this.userFiles=userFiles;

		this.setLayout(new GridLayout(2, false));

		if (userFiles==null) {
			this.setEnabled(false);
		}

		createContents();
	}

	private void createContents() {
		Label label;
		configurationItems = new HashMap<String, ConfigurationItemInterface>();

		label = new Label (this, SWT.NONE);
		label.setText("Plugin description:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));

		label = new Label (this, SWT.NONE);
		label.setText(pluginInfo.getDescription());
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		label = new Label (this, SWT.NONE);
		label.setText("Plugin configuration:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));

		label = new Label (this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		for (PluginParameter pluginParameter : pluginInfo.getParameters()) {

			label = new Label (this, SWT.NONE);
			label.setText(pluginParameter.getDisplayName());
			label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));

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
				configurationItems.put(parameterFile.getInternalName(), new ConfigurationItemFile(this, parameterFile, rumController));
				break; 
			default:
				break;
			}
		}

		label = new Label (this, SWT.NONE);
		label.setText("Plugin outputs:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));

		label = new Label (this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		for (PluginOutput pluginOutput : pluginInfo.getOutputs()) {
			label = new Label (this, SWT.NONE);
			label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));

			String fileTypeString = "";
			for (String fileType : pluginOutput.getFileTypes()) {
				fileTypeString+=fileType+", ";
			}
			if (!fileTypeString.isEmpty()) {
				fileTypeString=fileTypeString.substring(0, fileTypeString.length()-2);
			}

			label = new Label (this, SWT.NONE);
			label.setText(pluginOutput.getFileName() + " (" + fileTypeString + ")");
			label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		}
	}

	public Map<String, String> getConfigurationValues() {
		Map<String, String> configurationValues = new HashMap<String, String>();
		for (String key : configurationItems.keySet()) {
			String configurationItemValue = configurationItems.get(key).getValue();
			if (configurationItemValue!=null) {
				configurationValues.put(key, configurationItemValue);
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
	
	public List<UserFile> getUserFiles() {
		return userFiles;
	}
}
