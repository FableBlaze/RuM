package ee.ut.cs.rum.plugins.development.description.parameter;

import java.util.Arrays;

public class PluginParameterSelection extends PluginParameter {
	private boolean multiSelection;
	private String defaultValue;
	private PluginParameterSelectionItem[] selectionItems;
	
	public PluginParameterSelection() {
		super();
		super.setParameterType(PluginParameterType.SELECTION);
	}

	public boolean isMultiSelection() {
		return multiSelection;
	}

	public void setMultiSelection(boolean multiSelection) {
		this.multiSelection = multiSelection;
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
		return "PluginParameterSelection [multiSelection=" + multiSelection + ", defaultValue=" + defaultValue
				+ ", selectionItems=" + Arrays.toString(selectionItems) + "]";
	}
}
