package ee.ut.cs.rum.example.plugin.v1.utils;

import com.google.gson.Gson;

import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameter;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterDouble;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterInteger;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelectionItem;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterString;

public final class PluginConfiguration {
	
	private PluginConfiguration() {
	}
	
	public static String generatePluginInfoJSON() {
		PluginInfo pluginInfo = new PluginInfo();
		pluginInfo.setName("ToyPluginV1");
		pluginInfo.setDescription("This is the first toy plugin for RuM. This description is loaded form JSON.");
		
		PluginParameterSelection pluginParameterSelection1 = new PluginParameterSelection();
		pluginParameterSelection1.setDescription("Testing out the selection parameter");
		pluginParameterSelection1.setDisplayName("Selection");
		pluginParameterSelection1.setInternalName("sel1");
		pluginParameterSelection1.setDefaultValue("12");
		pluginParameterSelection1.setMultiSelection(false);
		pluginParameterSelection1.setRequired(true);

		PluginParameterSelectionItem pluginParameterSelectionItem1 = new PluginParameterSelectionItem();
		pluginParameterSelectionItem1.setDescription("The first selection item of first selection");
		pluginParameterSelectionItem1.setDisplayName("First");
		pluginParameterSelectionItem1.setInternalName("11");

		PluginParameterSelectionItem pluginParameterSelectionItem2 = new PluginParameterSelectionItem();
		pluginParameterSelectionItem2.setDescription("The second selection item of first selection");
		pluginParameterSelectionItem2.setDisplayName("Second");
		pluginParameterSelectionItem2.setInternalName("12");
		
		PluginParameterSelectionItem pluginParameterSelectionItem3 = new PluginParameterSelectionItem();
		pluginParameterSelectionItem3.setDescription("The third selection item of first selection");
		pluginParameterSelectionItem3.setDisplayName("Third");
		pluginParameterSelectionItem3.setInternalName("13");
		
		PluginParameterSelectionItem[] pluginParameterSelectionItems1 = {pluginParameterSelectionItem1,pluginParameterSelectionItem2,pluginParameterSelectionItem3};
		pluginParameterSelection1.setSelectionItems(pluginParameterSelectionItems1);
		
		PluginParameterSelection pluginParameterSelection2 = new PluginParameterSelection();
		pluginParameterSelection2.setDescription("Testing out the multi-selection parameter");
		pluginParameterSelection2.setDisplayName("Selection2");
		pluginParameterSelection2.setInternalName("sel2");
		pluginParameterSelection2.setMultiSelection(true);
		pluginParameterSelection2.setDefaultValue("23");
		pluginParameterSelection2.setRequired(true);
		
		PluginParameterSelectionItem pluginParameterSelectionItem4 = new PluginParameterSelectionItem();
		pluginParameterSelectionItem4.setDescription("The first selection item of second selection");
		pluginParameterSelectionItem4.setDisplayName("First");
		pluginParameterSelectionItem4.setInternalName("21");

		PluginParameterSelectionItem pluginParameterSelectionItem5 = new PluginParameterSelectionItem();
		pluginParameterSelectionItem5.setDescription("The second selection item of second selection");
		pluginParameterSelectionItem5.setDisplayName("Second");
		pluginParameterSelectionItem5.setInternalName("22");
		
		PluginParameterSelectionItem pluginParameterSelectionItem6 = new PluginParameterSelectionItem();
		pluginParameterSelectionItem6.setDescription("The third selection item of first selection");
		pluginParameterSelectionItem6.setDisplayName("Third");
		pluginParameterSelectionItem6.setInternalName("23");
		
		PluginParameterSelectionItem[] pluginParameterSelectionItems2 = {pluginParameterSelectionItem4,pluginParameterSelectionItem5,pluginParameterSelectionItem6};
		pluginParameterSelection2.setSelectionItems(pluginParameterSelectionItems2);
		
		
		PluginParameterDouble pluginParameterDouble = new PluginParameterDouble();
		pluginParameterDouble.setDecimalPlaces(2);
		pluginParameterDouble.setDefaultValue(1.987);
		pluginParameterDouble.setDescription("Testing the double parameter");
		pluginParameterDouble.setDisplayName("Double");
		pluginParameterDouble.setInternalName("doub1");
		pluginParameterDouble.setMaxValue(10.8);
		pluginParameterDouble.setMinValue(1.22);
		pluginParameterDouble.setRequired(true);
		
		
		PluginParameterFile pluginParameterFile = new PluginParameterFile();
		pluginParameterFile.setDescription("Testing the file parameter");
		pluginParameterFile.setDisplayName("File");
		pluginParameterFile.setInternalName("file1");
		pluginParameterFile.setRequired(true);
		
		
		PluginParameterInteger pluginParameterInteger = new PluginParameterInteger();
		pluginParameterInteger.setDefaultValue(122);
		pluginParameterInteger.setDescription("Thesting integer parameter");
		pluginParameterInteger.setDisplayName("Integer");
		pluginParameterInteger.setInternalName("inte1");
		pluginParameterInteger.setMaxValue(123);
		pluginParameterInteger.setMinValue(2);
		pluginParameterInteger.setRequired(true);
		
		
		PluginParameterString pluginParameterString = new PluginParameterString();
		pluginParameterString.setDefaultValue("defaultTextOfPlugin1");
		pluginParameterString.setDescription("Testing string parameter");
		pluginParameterString.setDisplayName("String");
		pluginParameterString.setInternalName("str1");
		pluginParameterString.setMaxInputLength(12);
		pluginParameterString.setRequired(true);
		
		PluginParameter[] parameters = {pluginParameterSelection1, pluginParameterSelection2, pluginParameterDouble, pluginParameterFile, pluginParameterInteger, pluginParameterString};
		
		pluginInfo.setParameters(parameters);
		
		Gson gson = new Gson();
		return gson.toJson(pluginInfo);
	}
}
