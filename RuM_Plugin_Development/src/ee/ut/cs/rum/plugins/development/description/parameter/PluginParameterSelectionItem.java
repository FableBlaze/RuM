package ee.ut.cs.rum.plugins.development.description.parameter;

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

	@Override
	public String toString() {
		return "PluginParameterSelectionItem [internalName=" + internalName + ", displayName=" + displayName
				+ ", description=" + description + "]";
	}

}
