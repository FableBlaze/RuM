package ee.ut.cs.rum.plugins.development.description;

import java.util.Arrays;

import com.google.gson.JsonParseException;

import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameter;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterType;

public class PluginInfo {

	private String name;
	private String description;
	private PluginInputObject[] inputObjects;
	private PluginParameter[] parameters;
	private PluginOutput[] outputs;

	public PluginInfo() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name==null || name.equals("")) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - name can not be empty");
		}
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description==null || description.equals("")) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - description can not be empty");
		}
		this.description = description;
	}

	public PluginInputObject[] getInputObjects() {
		return inputObjects;
	}

	public void setInputObjects(PluginInputObject[] inputObjects) {
		this.inputObjects = inputObjects;
		for (int i = 0; i < inputObjects.length; i++) {
			for (int j = i+1; j < inputObjects.length; j++) {
				if (inputObjects[i].getName().equals(inputObjects[j].getName())) {
					throw new JsonParseException(this.getClass().getSimpleName() + " - input object names must be unique");
				}
			}
		}
	}

	public PluginParameter[] getParameters() {
		return parameters;
	}

	public void setParameters(PluginParameter[] parameters) {
		if (parameters==null || parameters.length==0) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - parameters can not be empty");
		}
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].getParameterType().equals(PluginParameterType.LABEL)) {
				continue;
			} else {
				for (int j = i+1; j < parameters.length; j++) {
					if (parameters[j].getParameterType().equals(PluginParameterType.LABEL)) {
						continue;
					} else if (parameters[i].getInternalName().equals(parameters[j].getInternalName())) {
						throw new JsonParseException(this.getClass().getSimpleName() + " - parameter internal names must be unique");
					}
				}
			}
		}
		this.parameters = parameters;
	}

	public PluginOutput[] getOutputs() {
		return outputs;
	}

	public void setOutputs(PluginOutput[] outputs) {
		if (outputs==null || outputs.length==0) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - outputs can not be empty");
		}
		for (int i = 0; i < outputs.length; i++) {
			for (int j = i+1; j < outputs.length; j++) {
				if (outputs[i].getFileName().equals(outputs[j].getFileName())) {
					throw new JsonParseException(this.getClass().getSimpleName() + " - output filenames must be unique");
				}
			}
		}
		this.outputs = outputs;
	}

	@Override
	public String toString() {
		return "PluginInfo [name=" + name + ", description=" + description + ", parameters="
				+ Arrays.toString(parameters) + ", outputs=" + Arrays.toString(outputs) + "]";
	}
}
