package ee.ut.cs.rum.example.plugin.v4.utils;

import com.google.gson.Gson;

import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.PluginInputObject;
import ee.ut.cs.rum.plugins.development.description.PluginOutput;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameter;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterObjectList;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelectionItem;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterString;

public class PluginConfiguration {
	
	private PluginConfiguration() {
	}
	
	public static String generatePluginInfoJSON() {
		
		//General info
		PluginInfo pluginInfo = new PluginInfo();
		pluginInfo.setName("ToyPluginV4");
		pluginInfo.setDescription("For testing object_list parameter");
		
		//Outputs
		PluginOutput pluginOutput1 = new PluginOutput();
		pluginOutput1.setFileName("result.txt");
		String[] outputTypes1 = {"text", "txt"};
		pluginOutput1.setFileTypes(outputTypes1);
		
		PluginOutput[] pluginOutputs = {pluginOutput1};
		pluginInfo.setOutputs(pluginOutputs);
		
		//Input objects
		PluginInputObject pluginInputObject1 = new PluginInputObject();
		pluginInputObject1.setName("obj1");
		
		PluginParameterSelection obj1Parameter1Selection = new PluginParameterSelection();
		obj1Parameter1Selection.setInternalName("obj1sel1");
		obj1Parameter1Selection.setDisplayName("Selection");
		obj1Parameter1Selection.setDescription("Testing out the selection parameter");
		obj1Parameter1Selection.setRequired(true);
		obj1Parameter1Selection.setDefaultValue("12");

		PluginParameterSelectionItem obj1Parameter1SelectionItem1 = new PluginParameterSelectionItem();
		obj1Parameter1SelectionItem1.setInternalName("11");
		obj1Parameter1SelectionItem1.setDisplayName("First");
		obj1Parameter1SelectionItem1.setDescription("The first selection item of first selection");

		PluginParameterSelectionItem obj1Parameter1SelectionItem2 = new PluginParameterSelectionItem();
		obj1Parameter1SelectionItem2.setInternalName("12");
		obj1Parameter1SelectionItem2.setDisplayName("Second");
		obj1Parameter1SelectionItem2.setDescription("The second selection item of first selection");
		
		PluginParameterSelectionItem obj1Parameter1SelectionItem3 = new PluginParameterSelectionItem();
		obj1Parameter1SelectionItem3.setInternalName("13");
		obj1Parameter1SelectionItem3.setDisplayName("Third");
		obj1Parameter1SelectionItem3.setDescription("The third selection item of first selection");
		
		PluginParameterSelectionItem[] obj1Parameter1SelectionItems = {obj1Parameter1SelectionItem1,obj1Parameter1SelectionItem2,obj1Parameter1SelectionItem3};
		obj1Parameter1Selection.setSelectionItems(obj1Parameter1SelectionItems);
		
		
		PluginParameterSelection obj1Parameter2Selection = new PluginParameterSelection();
		obj1Parameter2Selection.setInternalName("obj1sel2");
		obj1Parameter2Selection.setDisplayName("Selection");
		obj1Parameter2Selection.setDescription("Testing out the selection parameter");
		obj1Parameter2Selection.setRequired(true);
		obj1Parameter2Selection.setDefaultValue("12");

		PluginParameterSelectionItem obj1Parameter2SelectionItem1 = new PluginParameterSelectionItem();
		obj1Parameter2SelectionItem1.setInternalName("11");
		obj1Parameter2SelectionItem1.setDisplayName("First");
		obj1Parameter2SelectionItem1.setDescription("The first selection item of first selection");

		PluginParameterSelectionItem obj1Parameter2SelectionItem2 = new PluginParameterSelectionItem();
		obj1Parameter2SelectionItem2.setInternalName("12");
		obj1Parameter2SelectionItem2.setDisplayName("Second");
		obj1Parameter2SelectionItem2.setDescription("The second selection item of first selection");
		
		PluginParameterSelectionItem obj1Parameter2SelectionItem3 = new PluginParameterSelectionItem();
		obj1Parameter2SelectionItem3.setInternalName("13");
		obj1Parameter2SelectionItem3.setDisplayName("Third");
		obj1Parameter2SelectionItem3.setDescription("The third selection item of first selection");
		
		PluginParameterSelectionItem[] obj1Parameter2SelectionItems = {obj1Parameter2SelectionItem1,obj1Parameter2SelectionItem2,obj1Parameter2SelectionItem3};
		obj1Parameter2Selection.setSelectionItems(obj1Parameter2SelectionItems);
		
		PluginParameterString obj1ParameterString = new PluginParameterString();
		obj1ParameterString.setInternalName("obj1str1");
		obj1ParameterString.setDisplayName("String");
		obj1ParameterString.setDescription("String");
		obj1ParameterString.setRequired(false);
		obj1ParameterString.setDefaultValue("default");
		obj1ParameterString.setMaxInputLength(10);
		
		PluginParameter[] obj1Parameters = {obj1Parameter1Selection, obj1Parameter2Selection, obj1ParameterString};
		pluginInputObject1.setParameters(obj1Parameters);
		
		PluginInputObject[] inputObjects = {pluginInputObject1};
		pluginInfo.setInputObjects(inputObjects);
		
		
		//Plugin parameters
		PluginParameterString pluginParameterString = new PluginParameterString();
		pluginParameterString.setInternalName("str1");
		pluginParameterString.setDisplayName("Random string");
		pluginParameterString.setDescription("Is not used by the plugin");
		pluginParameterString.setRequired(false);
		pluginParameterString.setDefaultValue("random");
		pluginParameterString.setMaxInputLength(30);
		
		PluginParameterObjectList pluginParameterObjectList = new PluginParameterObjectList();
		pluginParameterObjectList.setInternalName("objList1");
		pluginParameterObjectList.setDisplayName("Objects");
		pluginParameterObjectList.setDescription("Testing the object list parameter");
		pluginParameterObjectList.setRequired(true);
		pluginParameterObjectList.setInputObjectName("obj1");
		pluginParameterObjectList.setMinObjects(1);
		pluginParameterObjectList.setMaxObjects(5);
		
		PluginParameter[] parameters = {pluginParameterString, pluginParameterObjectList};
		pluginInfo.setParameters(parameters);
		
		Gson gson = new Gson();
		return gson.toJson(pluginInfo);
	}
}
