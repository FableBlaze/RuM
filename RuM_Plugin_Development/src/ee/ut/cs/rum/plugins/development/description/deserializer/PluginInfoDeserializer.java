package ee.ut.cs.rum.plugins.development.description.deserializer;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.PluginOutput;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameter;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterDouble;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterInteger;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelectionItem;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterString;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterType;

public class PluginInfoDeserializer implements JsonDeserializer<PluginInfo> {

	@Override
	public PluginInfo deserialize(JsonElement pluginInfoJson, Type typeOfT, JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {

		JsonObject pluginInfoJsonObject = pluginInfoJson.getAsJsonObject();

		PluginInfo pluginInfo = new PluginInfo();
		
		pluginInfo.setName(getAsStringFromJsonObject(pluginInfoJsonObject, "name"));
		pluginInfo.setDescription(getAsStringFromJsonObject(pluginInfoJsonObject, "description"));
		
		//Parse plugin parameters
		JsonArray pluginParametersJsonArray = getAsJsonArrayFromJsonObject(pluginInfoJsonObject, "parameters");
		PluginParameter[] pluginParameters = new PluginParameter[pluginParametersJsonArray.size()];
		for (int i = 0; i < pluginParameters.length; i++) {
			JsonObject pluginParameterJsonObject;
			try {
				pluginParameterJsonObject = pluginParametersJsonArray.get(i).getAsJsonObject();
			} catch (IllegalStateException e) {
				throw new JsonParseException("Plugin parameter object is invalid", e);
			}
			String parameterTypeString = getAsStringFromJsonObject(pluginParameterJsonObject, "parameterType");
			PluginParameterType parameterType;
			try {
				parameterType = PluginParameterType.valueOf(parameterTypeString);
			} catch (Exception e) {
				throw new JsonParseException("Unknown plugin parameter type: " + parameterTypeString, e);
			}

			switch (parameterType) {
			//TODO: Defaults for optional values need reviewing
			case STRING:
				PluginParameterString pluginParameterString = new PluginParameterString();
				pluginParameterString.setDefaultValue(getAsStringFromJsonObject(pluginParameterJsonObject, "defaultValue"));
				pluginParameterString.setMaxInputLength(getAsIntegerFromJsonObject(pluginParameterJsonObject, "maxInputLength"));
				pluginParameterString.setAllowedCharacters(getAsStringFromJsonObject(pluginParameterJsonObject, "allowedCharacters"));
				pluginParameters[i] = pluginParameterString;
				break; 
			case INTEGER:
				PluginParameterInteger parameterInteger = new PluginParameterInteger();
				parameterInteger.setDefaultValue(getAsIntegerFromJsonObject(pluginParameterJsonObject, "defaultValue"));
				parameterInteger.setMinValue(getAsIntegerFromJsonObject(pluginParameterJsonObject, "minValue"));
				parameterInteger.setMaxValue(getAsIntegerFromJsonObject(pluginParameterJsonObject, "maxValue"));
				pluginParameters[i] = parameterInteger;
				break; 
			case DOUBLE:
				PluginParameterDouble pluginParameterDouble = new PluginParameterDouble();
				pluginParameterDouble.setDefaultValue(getAsDoubleFromJsonObject(pluginParameterJsonObject, "defaultValue"));
				pluginParameterDouble.setMinValue(getAsDoubleFromJsonObject(pluginParameterJsonObject, "minValue"));
				pluginParameterDouble.setMaxValue(getAsDoubleFromJsonObject(pluginParameterJsonObject, "maxValue"));
				pluginParameterDouble.setDecimalPlaces(getAsIntegerFromJsonObject(pluginParameterJsonObject, "decimalPlaces"));
				pluginParameters[i] = pluginParameterDouble;
				break; 
			case SELECTION:
				PluginParameterSelection pluginParameterSelection = new PluginParameterSelection();
				pluginParameterSelection.setDefaultValue(getAsStringFromJsonObject(pluginParameterJsonObject, "defaultValue"));
				pluginParameterSelection.setSelectionItems(parseSelectionItems(pluginParameterJsonObject, pluginParameterSelection));
				pluginParameters[i] = pluginParameterSelection;
				break; 
			case FILE:
				PluginParameterFile pluginParameterFile = new PluginParameterFile();
				pluginParameterFile.setInputTypes(parseFileTypes(pluginParameterJsonObject, "inputTypes"));
				pluginParameters[i] = pluginParameterFile;
				break; 
			default:
				break;
			}

			if (pluginParameters[i]!=null) {
				pluginParameters[i].setInternalName(getAsStringFromJsonObject(pluginParameterJsonObject, "internalName"));
				pluginParameters[i].setDisplayName(getAsStringFromJsonObject(pluginParameterJsonObject, "displayName"));
				pluginParameters[i].setDescription(getAsStringFromJsonObject(pluginParameterJsonObject, "description"));
				pluginParameters[i].setRequired(getAsBooleanFromJsonObject(pluginParameterJsonObject, "required"));
			} else {
				return null;
			}
		}
		pluginInfo.setParameters(pluginParameters);
		
		//Parse plugin outputs
		JsonArray pluginOutputsJsonArray = getAsJsonArrayFromJsonObject(pluginInfoJsonObject, "outputs");
		PluginOutput[] pluginOutputs = new PluginOutput[pluginOutputsJsonArray.size()];
		for (int i = 0; i < pluginOutputs.length; i++) {
			JsonObject pluginOutputJsonObject;
			try {
				pluginOutputJsonObject = pluginOutputsJsonArray.get(i).getAsJsonObject();
			} catch (IllegalStateException e) {
				throw new JsonParseException("Plugin output object is invalid", e);
			}
			
			PluginOutput pluginOutput = new PluginOutput();
			pluginOutput.setFileName(getAsStringFromJsonObject(pluginOutputJsonObject, "fileName"));
			pluginOutput.setFileTypes(parseFileTypes(pluginOutputJsonObject, "fileTypes"));
			pluginOutputs[i]=pluginOutput;
		}

		pluginInfo.setOutputs(pluginOutputs);

		return pluginInfo;
	}
	
	private PluginParameterSelectionItem[] parseSelectionItems(JsonObject pluginParameterJsonObject, PluginParameterSelection pluginParameterSelection) {
		JsonArray selectionItemsJsonArray = getAsJsonArrayFromJsonObject(pluginParameterJsonObject, "selectionItems");
		PluginParameterSelectionItem[] selectionItems = new PluginParameterSelectionItem[selectionItemsJsonArray.size()];
		for (int j = 0; j < selectionItems.length; j++) {
			JsonObject selectionItemJsonObject = selectionItemsJsonArray.get(j).getAsJsonObject();
			PluginParameterSelectionItem parameterSelectionItem = new PluginParameterSelectionItem();
			parameterSelectionItem.setInternalName(getAsStringFromJsonObject(selectionItemJsonObject, "internalName"));
			parameterSelectionItem.setDisplayName(getAsStringFromJsonObject(selectionItemJsonObject, "displayName"));
			parameterSelectionItem.setDescription(getAsStringFromJsonObject(selectionItemJsonObject, "description"));
			selectionItems[j]=parameterSelectionItem;
		}
		return selectionItems;
	}
	
	private String[] parseFileTypes(JsonObject jsonObject, String memberName) {
		JsonArray fileTypesJsonArray = getAsJsonArrayFromJsonObject(jsonObject, memberName);
		String[] fileTypes = new String[fileTypesJsonArray.size()];
		
		for (int j = 0; j < fileTypes.length; j++) {
			fileTypes[j] = getFileTypeFromJsonArray(fileTypesJsonArray, j);
		}
		return fileTypes;
	}
	
	private String getFileTypeFromJsonArray(JsonArray jsonArray, int i) {
		try {
			String result = jsonArray.get(i).getAsString().toLowerCase();
			
			if (result.equals("")) {
				throw new JsonParseException("FileType can not be empty");
			} else {
				return result;
			}
		} catch (NullPointerException e) {
			throw new JsonParseException("FileType is rqeuired", e);
		} catch (ClassCastException e) {
			throw new JsonParseException("FileType is invalid", e);
		}
	}
	
	private JsonArray getAsJsonArrayFromJsonObject(JsonObject jsonObject, String memberName) {
		JsonArray result = new JsonArray();
		try {
			if (jsonObject.has(memberName) && !jsonObject.get(memberName).isJsonNull()) {
				result = jsonObject.get(memberName).getAsJsonArray();				
			}
		} catch (Exception e) {
			throw new JsonParseException(memberName + " is invalid", e);
		}
		return result;
	}
	
	private String getAsStringFromJsonObject(JsonObject jsonObject, String memberName) {
		String result = null;
		try {
			if (jsonObject.has(memberName) && !jsonObject.get(memberName).isJsonNull()) {
				result = jsonObject.get(memberName).getAsString();
			}
		} catch (Exception e) {
			throw new JsonParseException(memberName + " is invalid", e);
		}
		return result;
	}
	
	private Integer getAsIntegerFromJsonObject(JsonObject jsonObject, String memberName) {
		Integer result = null;
		try {
			if (jsonObject.has(memberName) && !jsonObject.get(memberName).isJsonNull()) {
				result = new Integer(jsonObject.get(memberName).getAsInt());
			}
		} catch (Exception e) {
			throw new JsonParseException(memberName + " is invalid", e);
		}
		return result;
	}
	
	private Double getAsDoubleFromJsonObject(JsonObject jsonObject, String memberName) {
		Double result = null;
		try {
			if (jsonObject.has(memberName) && !jsonObject.get(memberName).isJsonNull()) {
				result = new Double(jsonObject.get(memberName).getAsDouble()); 
			}
		} catch (Exception e) {
			throw new JsonParseException(memberName + " is invalid", e);
		}
		return result;
	}
	
	private Boolean getAsBooleanFromJsonObject(JsonObject jsonObject, String memberName) {
		Boolean result = null;
		try {
			if (jsonObject.has(memberName) && !jsonObject.get(memberName).isJsonNull()) {
				result = new Boolean(jsonObject.get(memberName).getAsBoolean()); 
			}
		} catch (Exception e) {
			throw new JsonParseException(memberName + " is invalid", e);
		}
		return result;
	}
}
