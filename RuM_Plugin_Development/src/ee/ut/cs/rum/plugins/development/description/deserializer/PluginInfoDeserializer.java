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
		
		pluginInfo.setName(getAsStringFromJsonObject(pluginInfoJsonObject, "name", true));
		pluginInfo.setDescription(getAsStringFromJsonObject(pluginInfoJsonObject, "description", true));
		
		JsonArray pluginOutputsJsonArray = getAsJsonArrayFromJsonObject(pluginInfoJsonObject, "outputs", true);
		PluginOutput[] pluginOutputs = new PluginOutput[pluginOutputsJsonArray.size()];
		
		for (int i = 0; i < pluginOutputs.length; i++) {
			JsonObject pluginOutputJsonObject;
			try {
				pluginOutputJsonObject = pluginOutputsJsonArray.get(i).getAsJsonObject();
			} catch (IllegalStateException e) {
				throw new JsonParseException("Plugin output object is invalid", e);
			}
			
			PluginOutput pluginOutput = new PluginOutput();
			pluginOutput.setFileName(getAsStringFromJsonObject(pluginOutputJsonObject, "fileName", true));
			pluginOutput.setFileTypes(parseFileTypes(pluginOutputJsonObject, "fileTypes"));
			pluginOutputs[i]=pluginOutput;
		}

		pluginInfo.setOutputs(pluginOutputs);
		
		JsonArray pluginParametersJsonArray = getAsJsonArrayFromJsonObject(pluginInfoJsonObject, "parameters", true);
		PluginParameter[] pluginParameters = new PluginParameter[pluginParametersJsonArray.size()];

		for (int i = 0; i < pluginParameters.length; i++) {
			JsonObject pluginParameterJsonObject;
			try {
				pluginParameterJsonObject = pluginParametersJsonArray.get(i).getAsJsonObject();
			} catch (IllegalStateException e) {
				throw new JsonParseException("Plugin parameter object is invalid", e);
			}
			String parameterTypeString = getAsStringFromJsonObject(pluginParameterJsonObject, "parameterType", true);
			PluginParameterType parameterType;
			try {
				parameterType = PluginParameterType.valueOf(parameterTypeString);
			} catch (IllegalArgumentException e) {
				throw new JsonParseException("Unknown plugin parameter type: " + parameterTypeString, e);
			}

			switch (parameterType) {
			//TODO: Defaults for optional values need reviewing
			case STRING:
				PluginParameterString pluginParameterString = new PluginParameterString();
				pluginParameterString.setDefaultValue(getAsStringFromJsonObject(pluginParameterJsonObject, "defaultValue", false));
				pluginParameterString.setMaxInputLength(getAsIntFromJsonObject(pluginParameterJsonObject, "maxInputLength", false));
				pluginParameterString.setAllowedCharacters(getAsStringFromJsonObject(pluginParameterJsonObject, "allowedCharacters", false));
				pluginParameters[i] = pluginParameterString;
				break; 
			case INTEGER:
				PluginParameterInteger parameterInteger = new PluginParameterInteger();
				parameterInteger.setDefaultValue(getAsIntFromJsonObject(pluginParameterJsonObject, "defaultValue", true));
				parameterInteger.setMinValue(getAsIntFromJsonObject(pluginParameterJsonObject, "minValue", true));
				parameterInteger.setMaxValue(getAsIntFromJsonObject(pluginParameterJsonObject, "maxValue", true));
				pluginParameters[i] = parameterInteger;
				break; 
			case DOUBLE:
				PluginParameterDouble pluginParameterDouble = new PluginParameterDouble();
				pluginParameterDouble.setDefaultValue(getAsDoubleFromJsonObject(pluginParameterJsonObject, "defaultValue", true));
				pluginParameterDouble.setMinValue(getAsDoubleFromJsonObject(pluginParameterJsonObject, "minValue", true));
				pluginParameterDouble.setMaxValue(getAsDoubleFromJsonObject(pluginParameterJsonObject, "maxValue", true));
				pluginParameterDouble.setDecimalPlaces(getAsIntFromJsonObject(pluginParameterJsonObject, "decimalPlaces", true));
				pluginParameters[i] = pluginParameterDouble;
				break; 
			case SELECTION:
				PluginParameterSelection pluginParameterSelection = new PluginParameterSelection();
				pluginParameterSelection.setDefaultValue(getAsStringFromJsonObject(pluginParameterJsonObject, "defaultValue", false));
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
				pluginParameters[i].setInternalName(getAsStringFromJsonObject(pluginParameterJsonObject, "internalName", true));
				pluginParameters[i].setDisplayName(getAsStringFromJsonObject(pluginParameterJsonObject, "displayName", true));
				pluginParameters[i].setDescription(getAsStringFromJsonObject(pluginParameterJsonObject, "description", true));
				pluginParameters[i].setRequired(getAsBooleanFromJsonObject(pluginParameterJsonObject, "required", true));
			} else {
				return null;
			}
		}

		pluginInfo.setParameters(pluginParameters);

		return pluginInfo;
	}
	
	private PluginParameterSelectionItem[] parseSelectionItems(JsonObject pluginParameterJsonObject, PluginParameterSelection pluginParameterSelection) {
		JsonArray selectionItemsJsonArray = getAsJsonArrayFromJsonObject(pluginParameterJsonObject, "selectionItems", true);
		boolean defaultOk=false;
		PluginParameterSelectionItem[] selectionItems = new PluginParameterSelectionItem[selectionItemsJsonArray.size()];
		for (int j = 0; j < selectionItems.length; j++) {
			JsonObject selectionItemJsonObject = selectionItemsJsonArray.get(j).getAsJsonObject();
			PluginParameterSelectionItem parameterSelectionItem = new PluginParameterSelectionItem();
			parameterSelectionItem.setInternalName(getAsStringFromJsonObject(selectionItemJsonObject, "internalName", true));
			parameterSelectionItem.setDisplayName(getAsStringFromJsonObject(selectionItemJsonObject, "displayName", true));
			parameterSelectionItem.setDescription(getAsStringFromJsonObject(selectionItemJsonObject, "description", false));
			if (parameterSelectionItem.getInternalName().equals(pluginParameterSelection.getDefaultValue())) {
				defaultOk=true;
			}
			selectionItems[j]=parameterSelectionItem;
		}
		
		if (!defaultOk && !pluginParameterSelection.getDefaultValue().equals("")) {
			throw new JsonParseException("Invalid default value");
		}
		return selectionItems;
	}
	
	private String[] parseFileTypes(JsonObject jsonObject, String memberName) {
		JsonArray fileTypesJsonArray = getAsJsonArrayFromJsonObject(jsonObject, memberName, true);
		String[] fileTypes = new String[fileTypesJsonArray.size()];
		
		for (int j = 0; j < fileTypes.length; j++) {
			fileTypes[j] = getFileTypeFromJsonArray(fileTypesJsonArray, j);
		}
		return fileTypes;
	}
	
	private String getAsStringFromJsonObject(JsonObject jsonObject, String memberName, boolean required) {
		try {
			String result = jsonObject.get(memberName).getAsString();
			
			if (required && result.equals("")) {
				throw new JsonParseException(memberName + " can not be empty");
			} else {
				return result;
			}
		} catch (NullPointerException e) {
			if (required) {
				throw new JsonParseException(memberName + " is rqeuired", e);
			} else {
				return "";				
			}
		} catch (ClassCastException e) {
			throw new JsonParseException(memberName + " is invalid", e);
		}
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
	
	private JsonArray getAsJsonArrayFromJsonObject(JsonObject jsonObject, String memberName, boolean required) {
		try {
			JsonArray result = jsonObject.get(memberName).getAsJsonArray();
			
			if (required && result.size()==0) {
				throw new JsonParseException(memberName + " can not be empty");
			} else {
				return result;
			}
		} catch (NullPointerException e) {
			if (required) {
				throw new JsonParseException(memberName + " is rqeuired", e);
			} else {
				return new JsonArray();				
			}
		} catch (IllegalStateException e) {
			throw new JsonParseException(memberName + " is invalid", e);
		}
	}
	
	private int getAsIntFromJsonObject(JsonObject jsonObject, String memberName, boolean required) {
		try {
			return jsonObject.get(memberName).getAsInt();
		} catch (NullPointerException e) {
			if (required) {
				throw new JsonParseException(memberName + " is rqeuired", e);
			} else {
				return 0;				
			}
		} catch (ClassCastException e) {
			throw new JsonParseException(memberName + " is invalid", e);
		}
	}
	
	private Double getAsDoubleFromJsonObject(JsonObject jsonObject, String memberName, boolean required) {
		try {
			return jsonObject.get(memberName).getAsDouble();
		} catch (NullPointerException e) {
			if (required) {
				throw new JsonParseException(memberName + " is rqeuired", e);
			} else {
				return 0.0;				
			}
		} catch (ClassCastException e) {
			throw new JsonParseException(memberName + " is invalid", e);
		}
	}
	
	private Boolean getAsBooleanFromJsonObject(JsonObject jsonObject, String memberName, boolean required) {
		try {
			return jsonObject.get(memberName).getAsBoolean();
		} catch (NullPointerException e) {
			if (required) {
				throw new JsonParseException(memberName + " is rqeuired", e);
			} else {
				return false;				
			}
		} catch (ClassCastException e) {
			throw new JsonParseException(memberName + " is invalid", e);
		}
	}
}
