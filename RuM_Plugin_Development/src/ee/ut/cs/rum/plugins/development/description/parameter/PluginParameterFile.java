package ee.ut.cs.rum.plugins.development.description.parameter;

import java.util.Arrays;

import com.google.gson.JsonParseException;

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
		if (inputTypes==null || inputTypes.length==0) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - inputTypes can not be empty");
		}
		this.inputTypes = inputTypes;
	}

	@Override
	public String toString() {
		return "PluginParameterFile [inputTypes=" + Arrays.toString(inputTypes) + "]";
	}
}
