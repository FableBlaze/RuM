package ee.ut.cs.rum.plugins.configuration.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.deserializer.PluginInfoDeserializer;

public final class PluginUtils {
	private PluginUtils() {
	}
	
	public static PluginInfo deserializePluginInfo(Plugin plugin) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(PluginInfo.class, new PluginInfoDeserializer());
		Gson gson = gsonBuilder.create();
		PluginInfo pluginInfo = gson.fromJson(plugin.getPluginInfo(), PluginInfo.class);
		
		return pluginInfo;
	}
}
