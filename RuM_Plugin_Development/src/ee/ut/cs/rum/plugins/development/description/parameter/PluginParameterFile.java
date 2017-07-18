package ee.ut.cs.rum.plugins.development.description.parameter;

import java.util.Arrays;

public class PluginParameterFile extends PluginParameter {
	
	private String[] inputTypes;
	
	public PluginParameterFile() {
		super();
		super.setParameterType(PluginParameterType.FILE);
	}

	public String[] getInputTypes() {
		return inputTypes;
	}

	public void setInputTypes(String[] inputTypes) {
		this.inputTypes = inputTypes;
	}

	@Override
	public String toString() {
		return "PluginParameterFile [inputTypes=" + Arrays.toString(inputTypes) + "]";
	}
}
