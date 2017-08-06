package ee.ut.cs.rum.example.plugin.v3;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import ee.ut.cs.rum.example.plugin.v2.factory.RumPluginWorkerImpl;
import ee.ut.cs.rum.plugins.development.interfaces.RumPluginFactory;
import ee.ut.cs.rum.plugins.development.interfaces.factory.RumPluginWorker;

public class RumPluginFactoryImpl implements RumPluginFactory {

	@Override
	public String getPluginInfoJSON() {
		String pluginInfoJSON = "";
		URL pluginInfoEntry = Activator.getContext().getBundle().getEntry("pluginInfo.json");
	    if (pluginInfoEntry != null) {
	    	InputStream input = null;
	    	Scanner scanner = null;
	        try {
				input = pluginInfoEntry.openStream();
				scanner = new Scanner(input);
				scanner.useDelimiter("\\A");
				pluginInfoJSON = scanner.hasNext() ? scanner.next() : "";
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (input!=null) {input.close();}
					if (scanner!=null) {scanner.close();}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
	    }
	    Activator.getLogger().info(pluginInfoJSON);
		return pluginInfoJSON;
	}

	@Override
	public RumPluginWorker createRumPluginWorker() {
		return new RumPluginWorkerImpl();
	}

}
