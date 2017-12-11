package ee.ut.cs.rum.plugins.development.description.deserializer;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.PluginInputObject;
import ee.ut.cs.rum.plugins.development.description.PluginOutput;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameter;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterDouble;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterInteger;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterObjectList;
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
		
		//Parse plugin inputObjects
		JsonArray pluginInputObjectsJsonArray = getAsJsonArrayFromJsonObject(pluginInfoJsonObject, "inputObjects");
		PluginInputObject[] pluginInputObjects = new PluginInputObject[pluginInputObjectsJsonArray.size()];
		for (int i = 0; i < pluginInputObjects.length; i++) {
			JsonObject pluginInputObjectJsonObject;
			try {
				pluginInputObjectJsonObject = pluginInputObjectsJsonArray.get(i).getAsJsonObject();
			} catch (IllegalStateException e) {
				throw new JsonParseException("Plugin parameter object is invalid", e);
			}
			PluginInputObject pluginInputObject = new PluginInputObject();
			pluginInputObject.setName(getAsStringFromJsonObject(pluginInputObjectJsonObject, "name"));
			
			JsonArray inputObjectParametersJsonArray = getAsJsonArrayFromJsonObject(pluginInputObjectJsonObject, "parameters");
			pluginInputObject.setParameters(parseParametersJsonArray(inputObjectParametersJsonArray));
			
			pluginInputObjects[i] = pluginInputObject;
		}
		pluginInfo.setInputObjects(pluginInputObjects);
		
		//Parse plugin parameters
		JsonArray pluginParametersJsonArray = getAsJsonArrayFromJsonObject(pluginInfoJsonObject, "parameters");
		pluginInfo.setParameters(parseParametersJsonArray(pluginParametersJsonArray));
		
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
	
	private PluginParameter[] parseParametersJsonArray(JsonArray parametersJsonArray) {
		PluginParameter[] parameters = new PluginParameter[parametersJsonArray.size()];
		for (int i = 0; i < parameters.length; i++) {
			JsonObject parameterJsonObject;
			try {
				parameterJsonObject = parametersJsonArray.get(i).getAsJsonObject();
			} catch (IllegalStateException e) {
				throw new JsonParseException("Parameter object is invalid", e);
			}
			String parameterTypeString = getAsStringFromJsonObject(parameterJsonObject, "parameterType");
			PluginParameterType parameterType;
			try {
				parameterType = PluginParameterType.valueOf(parameterTypeString);
			} catch (Exception e) {
				throw new JsonParseException("Unknown parameter type: " + parameterTypeString, e);
			}

			switch (parameterType) {
			//TODO: Defaults for optional values need reviewing
			case STRING:
				PluginParameterString pluginParameterString = new PluginParameterString();
				pluginParameterString.setDefaultValue(getAsStringFromJsonObject(parameterJsonObject, "defaultValue"));
				pluginParameterString.setMaxInputLength(getAsIntegerFromJsonObject(parameterJsonObject, "maxInputLength"));
				pluginParameterString.setAllowedCharacters(getAsStringFromJsonObject(parameterJsonObject, "allowedCharacters"));
				parameters[i] = pluginParameterString;
				break; 
			case INTEGER:
				PluginParameterInteger parameterInteger = new PluginParameterInteger();
				parameterInteger.setDefaultValue(getAsIntegerFromJsonObject(parameterJsonObject, "defaultValue"));
				parameterInteger.setMinValue(getAsIntegerFromJsonObject(parameterJsonObject, "minValue"));
				parameterInteger.setMaxValue(getAsIntegerFromJsonObject(parameterJsonObject, "maxValue"));
				parameters[i] = parameterInteger;
				break; 
			case DOUBLE:
				PluginParameterDouble pluginParameterDouble = new PluginParameterDouble();
				pluginParameterDouble.setDefaultValue(getAsDoubleFromJsonObject(parameterJsonObject, "defaultValue"));
				pluginParameterDouble.setMinValue(getAsDoubleFromJsonObject(parameterJsonObject, "minValue"));
				pluginParameterDouble.setMaxValue(getAsDoubleFromJsonObject(parameterJsonObject, "maxValue"));
				pluginParameterDouble.setDecimalPlaces(getAsIntegerFromJsonObject(parameterJsonObject, "decimalPlaces"));
				parameters[i] = pluginParameterDouble;
				break; 
			case SELECTION:
				PluginParameterSelection pluginParameterSelection = new PluginParameterSelection();
				pluginParameterSelection.setDefaultValue(getAsStringFromJsonObject(parameterJsonObject, "defaultValue"));
				pluginParameterSelection.setSelectionItems(parseSelectionItems(parameterJsonObject, pluginParameterSelection));
				parameters[i] = pluginParameterSelection;
				break; 
			case FILE:
				PluginParameterFile pluginParameterFile = new PluginParameterFile();
				pluginParameterFile.setInputTypes(parseFileTypes(parameterJsonObject, "inputTypes"));
				parameters[i] = pluginParameterFile;
				break;
			case OBJECT_LIST:
				PluginParameterObjectList pluginParameterObjectList = new PluginParameterObjectList();
				pluginParameterObjectList.setInputObjectName(getAsStringFromJsonObject(parameterJsonObject, "inputObjectName"));
				pluginParameterObjectList.setMinObjects(getAsIntegerFromJsonObject(parameterJsonObject, "minObjects"));
				pluginParameterObjectList.setMaxObjects(getAsIntegerFromJsonObject(parameterJsonObject, "maxObjects"));
				parameters[i] = pluginParameterObjectList;
			default:
				break;
			}

			if (parameters[i]!=null) {
				parameters[i].setInternalName(getAsStringFromJsonObject(parameterJsonObject, "internalName"));
				parameters[i].setDisplayName(getAsStringFromJsonObject(parameterJsonObject, "displayName"));
				parameters[i].setDescription(getAsStringFromJsonObject(parameterJsonObject, "description"));
				parameters[i].setRequired(getAsBooleanFromJsonObject(parameterJsonObject, "required"));
			} else {
				return null;
			}
		}
		return parameters;
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
