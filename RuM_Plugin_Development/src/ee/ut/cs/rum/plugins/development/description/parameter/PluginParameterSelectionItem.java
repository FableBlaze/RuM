package ee.ut.cs.rum.plugins.development.description.parameter;

import com.google.gson.JsonParseException;

public class PluginParameterSelectionItem {
	private String internalName;
	private String displayName;
	private String description;

	public PluginParameterSelectionItem() {
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

	@Override
	public String toString() {
		return "PluginParameterSelectionItem [internalName=" + internalName + ", displayName=" + displayName
				+ ", description=" + description + "]";
	}
}
