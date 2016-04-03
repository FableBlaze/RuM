package ee.ut.cs.rum.plugins.development.description.parameter;

public class PluginParameter {
	private String internalName;
	private String displayName;
	private String description;
	private boolean required;
	private PluginParameterType parameterType;
	
	protected PluginParameter() {
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

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public PluginParameterType getParameterType() {
		return parameterType;
	}

	protected void setParameterType(PluginParameterType parameterType) {
		this.parameterType = parameterType;
	}

	@Override
	public String toString() {
		return "PluginParameterInfo [internalName=" + internalName + ", displayName=" + displayName + ", description="
				+ description + ", required=" + required + ", parameterType=" + parameterType + "]";
	}
	
}
