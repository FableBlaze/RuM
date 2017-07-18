package ee.ut.cs.rum.plugins.development.description.parameter;

import java.util.Arrays;

public class PluginParameterSelection extends PluginParameter {
	private String defaultValue;
	private PluginParameterSelectionItem[] selectionItems;
	
	public PluginParameterSelection() {
		super();
		super.setParameterType(PluginParameterType.SELECTION);
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public PluginParameterSelectionItem[] getSelectionItems() {
		return selectionItems;
	}

	public void setSelectionItems(PluginParameterSelectionItem[] selectionItems) {
		this.selectionItems = selectionItems;
	}

	@Override
	public String toString() {
		return "PluginParameterSelection [defaultValue=" + defaultValue + ", selectionItems="
				+ Arrays.toString(selectionItems) + "]";
	}
}
