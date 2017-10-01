package ee.ut.cs.rum.plugins.configuration.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTaskDependency;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.domain.UserFileType;
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

	private List<ConfigurationItemInterface> configurationItems;
	private List<ConfigurationItemFile> configurationItemFiles;
	private List<UserFile> userFiles;
	private List<UserFile> taskUserFiles;
	private List<UserFile> tmpUserFiles;
	private List<UserFile> outputUserFiles;

	public PluginConfigurationComposite(ScrolledPluginConfigurationComposite parent, PluginInfo pluginInfo) {
		super(parent, SWT.NONE);

		this.setEnabled(false);
		setLayout();
		createContents(pluginInfo);
	}

	public PluginConfigurationComposite(Composite parent, PluginInfo pluginInfo, RumController rumController, List<UserFile> userFiles, List<UserFile> taskUserFiles, List<UserFile> tmpUserFiles) {
		super(parent, SWT.NONE);

		this.rumController=rumController;
		this.userFiles=userFiles;
		this.taskUserFiles=taskUserFiles;
		this.tmpUserFiles=tmpUserFiles;

		this.outputUserFiles = new ArrayList<UserFile>();
		for (PluginOutput pluginOutput : pluginInfo.getOutputs()) {
			UserFile userFile = new UserFile();
			userFile.setOriginalFilename(pluginOutput.getFileName());
			List<UserFileType> userFileTypes = new ArrayList<UserFileType>();
			for (String fileType : pluginOutput.getFileTypes()) {
				UserFileType userFileType = new UserFileType();
				userFileType.setTypeName(fileType);
				userFileTypes.add(userFileType);
			}
			userFile.setUserFileTypes(userFileTypes);
			this.outputUserFiles.add(userFile);
		}

		setLayout();
		createContents(pluginInfo);
	}

	private void setLayout() {
		GridLayout layout = new GridLayout(2, false);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		this.setLayout(layout);
	}

	private void createContents(PluginInfo pluginInfo) {
		Label label;
		configurationItems = new ArrayList<ConfigurationItemInterface>();
		configurationItemFiles = new ArrayList<ConfigurationItemFile>();

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
			if (pluginParameter.getRequired()) {
				label.setText(pluginParameter.getDisplayName()+" *");				
			} else {
				label.setText(pluginParameter.getDisplayName()+"  ");
			}
			label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));

			switch (pluginParameter.getParameterType()) {
			case STRING:
				PluginParameterString parameterString = (PluginParameterString) pluginParameter;
				configurationItems.add(new ConfigurationItemString(this, parameterString));
				break; 
			case INTEGER:
				PluginParameterInteger parameterInteger = (PluginParameterInteger) pluginParameter;
				configurationItems.add(new ConfigurationItemInteger(this, parameterInteger));
				break; 
			case DOUBLE:
				PluginParameterDouble parameterDouble = (PluginParameterDouble) pluginParameter;
				configurationItems.add(new ConfigurationItemDouble(this, parameterDouble));
				break; 
			case SELECTION:
				PluginParameterSelection parameterSelection = (PluginParameterSelection) pluginParameter;
				configurationItems.add(new ConfigurationItemSelection(this, parameterSelection));
				break; 
			case FILE:
				PluginParameterFile parameterFile = (PluginParameterFile) pluginParameter;
				configurationItemFiles.add(new ConfigurationItemFile(this, parameterFile, rumController));
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

	public List<String> getDisplayNamesOfEmptyRequiredParameters() {
		List<String> displayNames = new ArrayList<String>();

		for (ConfigurationItemInterface configurationItem : configurationItems) {
			if (configurationItem.getRequired() && (configurationItem.getValue()==null || configurationItem.getValue().equals(""))) {
				displayNames.add(configurationItem.getDisplayName());
			}
		}
		for (ConfigurationItemFile configurationItemFile : configurationItemFiles) {
			if (configurationItemFile.getRequired() && configurationItemFile.getValue()==null) {
				displayNames.add(configurationItemFile.getDisplayName());
			}
		}

		return displayNames;
	}

	public Map<String, String> getConfigurationValues() {
		Map<String, String> configurationValues = new HashMap<String, String>();
		for (ConfigurationItemInterface configurationItem : configurationItems) {
			if (configurationItem.getValue()!=null) {
				configurationValues.put(configurationItem.getInternalName(), configurationItem.getValue());
			} else {
				configurationValues.put(configurationItem.getInternalName(), "");
			}
		}

		for (ConfigurationItemFile configurationItemFile : configurationItemFiles) {
			if (configurationItemFile.getValue()!=null) {
				configurationValues.put(configurationItemFile.getInternalName(), configurationItemFile.getValue());
			} else {
				configurationValues.put(configurationItemFile.getInternalName(), "");
			}
		}

		return configurationValues;
	}

	public List<SubTaskDependency> getDependsOn() {
		List<SubTaskDependency> dependsOn = new ArrayList<SubTaskDependency>();
		for (ConfigurationItemFile configurationItemFile : configurationItemFiles) {
			if (configurationItemFile.getValue().equals("") && configurationItemFile.getDependsOnFile()!=null) {
				SubTaskDependency subTaskDependency = new SubTaskDependency();
				subTaskDependency.setRequiredForParameter(configurationItemFile.getInternalName());
				subTaskDependency.setFileName(configurationItemFile.getDependsOnFile().getOriginalFilename());
				subTaskDependency.setFulfilledBySubTask(configurationItemFile.getDependsOnFile().getSubTask());
				dependsOn.add(subTaskDependency);
			}
		}
		return dependsOn;
	}

	public void setConfigurationValues(Map<String, String> configurationValues) {
		for (ConfigurationItemInterface configurationItem : configurationItems) {
			if (configurationValues.get(configurationItem.getInternalName())!=null) {
				configurationItem.setValue(configurationValues.get(configurationItem.getInternalName()));				
			}
		}
		for (ConfigurationItemFile configurationItemFile : configurationItemFiles) {
			if (configurationValues.get(configurationItemFile.getInternalName())!=null) {
				configurationItemFile.setValue(configurationValues.get(configurationItemFile.getInternalName()));				
			}
		}
	}

	public List<UserFile> getUserFiles() {
		return userFiles;
	}

	public List<UserFile> getTaskUserFiles() {
		return taskUserFiles;
	}

	public List<UserFile> getTmpUserFiles() {
		return tmpUserFiles;
	}

	public List<UserFile> getOutputUserFiles() {
		return outputUserFiles;
	}

	public List<ConfigurationItemFile> getConfigurationItemFiles() {
		return configurationItemFiles;
	}

	public void notifySubTaskOfPluginSelect(List<UserFile> outputFiles) {
		if (outputUserFiles!=outputFiles) {
			for (ConfigurationItemFile configurationItemFile : configurationItemFiles) {
				configurationItemFile.notifyFileParameterOfPluginSelect(outputFiles);
			}			
		}
	}

	public void notifySubTaskOfPluginDeselect(List<UserFile> outputFiles) {
		if (outputUserFiles!=outputFiles) {
			for (ConfigurationItemFile configurationItemFile : configurationItemFiles) {
				configurationItemFile.notifyFileParameterOfPluginDeselect(outputFiles);
			}			
		}
	}

	public void notifySubTaskOfSubTaskNameChange(List<UserFile> outputFiles) {
		if (outputUserFiles!=outputFiles) {
			for (ConfigurationItemFile configurationItemFile : configurationItemFiles) {
				configurationItemFile.notifyFileParameterOfSubTaskNameChange(outputFiles);
			}			
		}
	}

	public void notifySubTaskOfTmpFileUpload(UserFile tmpUserFile) {
		for (ConfigurationItemFile configurationItemFile : configurationItemFiles) {
			configurationItemFile.notifyFileParameterOfTmpFileUpload(tmpUserFile);
		}
	}

	public void addUserFile(UserFile userFile) {
		for (ConfigurationItemFile configurationItemFile : configurationItemFiles) {
			configurationItemFile.addUserFile(userFile);
		}
	}

	public void modifyUserFile(UserFile userFile) {
		for (ConfigurationItemFile configurationItemFile : configurationItemFiles) {
			configurationItemFile.modifyUserFile(userFile);
		}
	}

	public void removeUserFile(UserFile userFile) {
		for (ConfigurationItemFile configurationItemFile : configurationItemFiles) {
			configurationItemFile.removeUserFile(userFile);
		}
	}

	public UserFile addTmpUserFile(UserFile tmpUserFile) {
		synchronized (tmpUserFiles) {
			tmpUserFiles.add(tmpUserFile);
			tmpUserFile = tmpUserFiles.get(tmpUserFiles.size()-1);
		}
		return tmpUserFile;
	}

}
