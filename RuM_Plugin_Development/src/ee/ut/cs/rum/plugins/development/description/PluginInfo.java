package ee.ut.cs.rum.plugins.development.description;

import java.util.Arrays;

import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameter;

public class PluginInfo {
	
	private String name;
	private String description;
	private PluginParameter[] parameters;
	private PluginOutput[] outputs;

	public PluginInfo() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PluginParameter[] getParameters() {
		return parameters;
	}

	public void setParameters(PluginParameter[] parameters) {
		this.parameters = parameters;
	}

	public PluginOutput[] getOutputs() {
		return outputs;
	}

	public void setOutputs(PluginOutput[] outputs) {
		this.outputs = outputs;
	}

	@Override
	public String toString() {
		return "PluginInfo [name=" + name + ", description=" + description + ", parameters="
				+ Arrays.toString(parameters) + ", outputs=" + Arrays.toString(outputs) + "]";
	}

}
