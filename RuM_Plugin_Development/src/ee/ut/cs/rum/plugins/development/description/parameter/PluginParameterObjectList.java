package ee.ut.cs.rum.plugins.development.description.parameter;

import com.google.gson.JsonParseException;

public class PluginParameterObjectList extends PluginParameter {
	private String inputObjectName;
	private Integer minObjects;
	private Integer maxObjects;
	
	public PluginParameterObjectList() {
		super(PluginParameterType.OBJECT_LIST);
	}
	
	public String getInputObjectName() {
		return inputObjectName;
	}
	
	public void setInputObjectName(String inputObjectName) {
		if (inputObjectName==null || inputObjectName.equals("")) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - inputObjectName can not be empty");
		}
		this.inputObjectName = inputObjectName;
	} 

	public Integer getMinObjects() {
		return minObjects;
	}
	
	public void setMinObjects(Integer minObjects) {
		if (minObjects!=null && minObjects<0) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - minObjects can not be negative");
		}
		this.minObjects = minObjects;
	}
	
	public Integer getMaxObjects() {
		return maxObjects;
	}
	
	public void setMaxObjects(Integer maxObjects) {
		if (maxObjects!=null && maxObjects<0) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - maxObjects can not be negative");
		}
		this.maxObjects = maxObjects;
	}
	
}
