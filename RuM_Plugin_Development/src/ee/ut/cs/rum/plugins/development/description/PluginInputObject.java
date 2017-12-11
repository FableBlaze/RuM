package ee.ut.cs.rum.plugins.development.description;

import com.google.gson.JsonParseException;

import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameter;

public class PluginInputObject {
	private String name;
	private PluginParameter[] parameters;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (name==null || name.equals("")) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - name can not be empty");
		}
		this.name = name;
	}
	
	public PluginParameter[] getParameters() {
		return parameters;
	}
	
	public void setParameters(PluginParameter[] parameters) {
		if (parameters==null || parameters.length==0) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - parameters can not be empty");
		}
		for (int i = 0; i < parameters.length; i++) {
			for (int j = i+1; j < parameters.length; j++) {
				if (parameters[i].getInternalName().equals(parameters[j].getInternalName())) {
					throw new JsonParseException(this.getClass().getSimpleName() + " - parameter internal names must be unique");
				}
			}
		}
		this.parameters = parameters;
	}
}
