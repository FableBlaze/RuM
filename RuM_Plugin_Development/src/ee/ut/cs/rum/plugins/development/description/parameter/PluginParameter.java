package ee.ut.cs.rum.plugins.development.description.parameter;

import com.google.gson.JsonParseException;

public class PluginParameter {
	private String internalName;
	private String displayName;
	private String description;
	private Boolean required;
	private PluginParameterType parameterType;
	
	protected PluginParameter(PluginParameterType parameterType) {
		if (parameterType==null) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - parameterType can not be empty");
		}
		this.parameterType = parameterType;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		if (internalName==null || internalName.equals("")) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - internalName can not be empty");
		}
		this.internalName = internalName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		if (displayName==null || displayName.equals("")) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - displayName can not be empty");
		}
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		if (required==null) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - required can not be empty");
		}
		this.required = required;
	}

	public PluginParameterType getParameterType() {
		return parameterType;
	}

	@Override
	public String toString() {
		return "PluginParameter [internalName=" + internalName + ", displayName=" + displayName + ", description="
				+ description + ", required=" + required + ", parameterType=" + parameterType + "]";
	}
}
