package ee.ut.cs.rum.plugins.description;

public class PluginParameterInfo {
	private String internalName;
	private String displayName;
	private String description;
	private PluginParameterTypeEnum parameterType;
	private boolean required;
	private String defaultValue;
	
	public PluginParameterInfo() {
	}
	
	public PluginParameterInfo(String internalName, String displayName, String description, PluginParameterTypeEnum parameterType, boolean required, String defaultValue) {
		this.internalName=internalName;
		this.displayName=displayName;
		this.description=description;
		this.parameterType=parameterType;
		this.required=required;
		this.defaultValue=defaultValue;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PluginParameterTypeEnum getParameterType() {
		return parameterType;
	}

	public void setParameterType(PluginParameterTypeEnum parameterType) {
		this.parameterType = parameterType;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "PluginParameterInfo [internalName=" + internalName + ", displayName=" + displayName + ", description="
				+ description + ", parameterType=" + parameterType + ", required=" + required + ", defaultValue="
				+ defaultValue + "]";
	}
	
}
