package ee.ut.cs.rum.plugins.development.description.parameter;

import java.util.Arrays;

import com.google.gson.JsonParseException;

public class PluginParameterSelection extends PluginParameter {
	private String defaultValue;
	private PluginParameterSelectionItem[] selectionItems;
	
	public PluginParameterSelection() {
		super(PluginParameterType.SELECTION);
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		if (defaultValue!=null && defaultValue.equals("")) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - defaultValue can not be empty string");
		}
		if (selectionItems!=null) {
			boolean defaultOk=false;
			for (PluginParameterSelectionItem pluginParameterSelectionItem : selectionItems) {
				if (pluginParameterSelectionItem.getInternalName()==defaultValue) {
					defaultOk=true;
				}
			}
			if (!defaultOk) {
				throw new JsonParseException("Invalid default value");
			}
		}
	}

	public PluginParameterSelectionItem[] getSelectionItems() {
		return selectionItems;
	}

	public void setSelectionItems(PluginParameterSelectionItem[] selectionItems) {
		if (selectionItems==null || selectionItems.length==0) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - selectionItems can not be empty");
		}
		for (int i = 0; i < selectionItems.length; i++) {
			for (int j = i+1; j < selectionItems.length; j++) {
				if (selectionItems[i].getInternalName().equals(selectionItems[j].getInternalName())) {
					throw new JsonParseException(this.getClass().getSimpleName() + " - selectionItem internal names must be unique");
				}
			}
		}
		if (defaultValue!=null) {
			boolean defaultOk=false;
			for (PluginParameterSelectionItem pluginParameterSelectionItem : selectionItems) {
				if (pluginParameterSelectionItem.getInternalName()==defaultValue) {
					defaultOk=true;
				}
			}
			if (!defaultOk) {
				throw new JsonParseException("Invalid default value");
			}
		}
		this.selectionItems = selectionItems;
	}

	@Override
	public String toString() {
		return "PluginParameterSelection [defaultValue=" + defaultValue + ", selectionItems="
				+ Arrays.toString(selectionItems) + "]";
	}
}
