package ee.ut.cs.rum.example.plugin.v5.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import ee.ut.cs.rum.example.plugin.v5.Activator;
import ee.ut.cs.rum.plugins.development.interfaces.factory.RumPluginWorker;

public class RumPluginWorkerImpl implements RumPluginWorker {

	@Override
	public int runWork(String configuration, File outputParent) {
		Activator.getLogger().info("RuM_ToyPlugin4 configuration: " + configuration);
		Activator.getLogger().info("RuM_ToyPlugin4 outputParent: " + outputParent.getPath());
		
		try {
			//Placeholder file for testing the outputs
			PrintWriter writer = new PrintWriter(new File(outputParent, "result.txt"));
			writer.println("Placeholder");
			writer.close();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		Activator.getLogger().info("RuM_ToyPlugin4 done");
		
		return 0;
	}
}
