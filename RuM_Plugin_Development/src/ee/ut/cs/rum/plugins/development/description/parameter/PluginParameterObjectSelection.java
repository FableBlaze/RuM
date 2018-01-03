package ee.ut.cs.rum.plugins.development.description.parameter;

import com.google.gson.JsonParseException;

public class PluginParameterObjectSelection extends PluginParameter {
	private String inputObjectListName;
	
	public PluginParameterObjectSelection() {
		super();
		super.setParameterType(PluginParameterType.OBJECT_SELECTION);
	}

	public String getInputObjectListName() {
		return inputObjectListName;
	}

	public void setInputObjectListName(String inputObjectListName) {
		if (inputObjectListName==null || inputObjectListName.equals("")) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - inputObjectListName can not be empty");
		}
		this.inputObjectListName = inputObjectListName;
	}

	
}
