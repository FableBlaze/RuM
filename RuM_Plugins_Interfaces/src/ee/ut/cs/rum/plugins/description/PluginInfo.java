package ee.ut.cs.rum.plugins.description;

import java.util.Arrays;

public class PluginInfo {
	
	private String name;
	private String description;
	private PluginParameterInfo[] parameters;

	public PluginInfo() {
	}
	
	public PluginInfo(String name, String description, PluginParameterInfo[] parameters) {
		this.name=name;
		this.description=description;
		this.parameters=parameters;
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

	public PluginParameterInfo[] getParameters() {
		return parameters;
	}

	public void setParameters(PluginParameterInfo[] parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "PluginInfo [name=" + name + ", description=" + description + ", parameters="
				+ Arrays.toString(parameters) + "]";
	}
}
