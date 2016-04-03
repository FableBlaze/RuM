package ee.ut.cs.rum.plugins.description.deserializer;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import ee.ut.cs.rum.plugins.description.PluginInfo;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameter;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameterDouble;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameterFile;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameterInteger;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameterSelectionItem;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameterString;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameterType;

public class PluginInfoDeserializer implements JsonDeserializer<PluginInfo> {

	@Override
	public PluginInfo deserialize(JsonElement pluginInfoJson, Type typeOfT, JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {

		JsonObject pluginInfoJsonObject = pluginInfoJson.getAsJsonObject();

		PluginInfo pluginInfo = new PluginInfo();
		pluginInfo.setName(pluginInfoJsonObject.get("name").getAsString());
		pluginInfo.setDescription(pluginInfoJsonObject.get("description").getAsString());

		JsonArray PluginParametersJsonArray = pluginInfoJsonObject.get("parameters").getAsJsonArray();
		PluginParameter[] pluginParameters = new PluginParameter[PluginParametersJsonArray.size()];

		for (int i = 0; i < pluginParameters.length; i++) {
			JsonObject pluginParameterJsonObject = PluginParametersJsonArray.get(i).getAsJsonObject();
			String parameterTypeString = pluginParameterJsonObject.get("parameterType").getAsString();
			PluginParameterType parameterType = PluginParameterType.valueOf(parameterTypeString);

			switch (parameterType) {
			case STRING:
				PluginParameterString pluginParameterString = new PluginParameterString();
				pluginParameterString.setMaxInputLength(pluginParameterJsonObject.get("maxInputLength").getAsInt());
				pluginParameterString.setDefaultValue(pluginParameterJsonObject.get("defaultValue").getAsString());
				pluginParameters[i] = pluginParameterString;
				break; 
			case INTEGER:
				PluginParameterInteger parameterInteger = new PluginParameterInteger();
				parameterInteger.setMinValue(pluginParameterJsonObject.get("minValue").getAsInt());
				parameterInteger.setMaxValue(pluginParameterJsonObject.get("maxValue").getAsInt());
				parameterInteger.setDefaultValue(pluginParameterJsonObject.get("defaultValue").getAsInt());
				pluginParameters[i] = parameterInteger;
				break; 
			case DOUBLE:
				PluginParameterDouble pluginParameterDouble = new PluginParameterDouble();
				pluginParameterDouble.setMinValue(pluginParameterJsonObject.get("minValue").getAsDouble());
				pluginParameterDouble.setMaxValue(pluginParameterJsonObject.get("maxValue").getAsDouble());
				pluginParameterDouble.setDefaultValue(pluginParameterJsonObject.get("defaultValue").getAsDouble());
				pluginParameterDouble.setDecimalPlaces(pluginParameterJsonObject.get("minValue").getAsInt());
				pluginParameters[i] = pluginParameterDouble;
				break; 
			case SELECTION:
				PluginParameterSelection pluginParameterSelection = new PluginParameterSelection();
				pluginParameterSelection.setMultiSelection(pluginParameterJsonObject.get("multiSelection").getAsBoolean());
				
				JsonArray selectionItemsJsonArray = pluginParameterJsonObject.get("selectionItems").getAsJsonArray();
				PluginParameterSelectionItem[] selectionItems = new PluginParameterSelectionItem[selectionItemsJsonArray.size()];
				
				for (int j = 0; j < selectionItems.length; j++) {
					JsonObject selectionItemJsonObject = selectionItemsJsonArray.get(j).getAsJsonObject();
					PluginParameterSelectionItem parameterSelectionItem = new PluginParameterSelectionItem();
					parameterSelectionItem.setInternalName(selectionItemJsonObject.get("internalName").getAsString());
					parameterSelectionItem.setDisplayName(selectionItemJsonObject.get("displayName").getAsString());
					parameterSelectionItem.setDescription(selectionItemJsonObject.get("description").getAsString());
					selectionItems[j]=parameterSelectionItem;
				}
				pluginParameterSelection.setSelectionItems(selectionItems);
				pluginParameters[i] = pluginParameterSelection;
				break; 
			case FILE:
				PluginParameterFile pluginParameterFile = new PluginParameterFile();
				pluginParameters[i] = pluginParameterFile;
				break; 
			default:
				break;
			}

			if (pluginParameters[i]!=null) {
				pluginParameters[i].setInternalName(pluginParameterJsonObject.get("internalName").getAsString());
				pluginParameters[i].setDisplayName(pluginParameterJsonObject.get("displayName").getAsString());
				pluginParameters[i].setDescription(pluginParameterJsonObject.get("description").getAsString());
				pluginParameters[i].setRequired(pluginParameterJsonObject.get("required").getAsBoolean());
			} else {
				return null;
			}
		}

		pluginInfo.setParameters(pluginParameters);

		return pluginInfo;
	}

}
