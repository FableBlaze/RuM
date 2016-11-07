package ee.ut.cs.rum.example.plugin.v2.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import ee.ut.cs.rum.example.plugin.v2.Activator;
import ee.ut.cs.rum.plugins.development.interfaces.factory.RumPluginWorker;

public class RumPluginWorkerImpl implements RumPluginWorker {

	@Override
	public int runWork(String configuration, File outputParent) {
		// TODO Do something with parameters
		Activator.getLogger().info("RuM_ToyPlugin2 configuration: " + configuration);
		Activator.getLogger().info("RuM_ToyPlugin2 outputParent: " + outputParent.getPath());
		
		try {
			PrintWriter writer = new PrintWriter(new File(outputParent, "textAndCSV.out"));
			writer.println("RuM_ToyPlugin2 configuration:");
			writer.println(configuration);
			writer.close();
			
			//Placeholder file for testing the outputs
			writer = new PrintWriter(new File(outputParent, "gif.out"));
			writer.println("Placeholder");
			writer.close();
			
			//Placeholder file for testing the outputs
			writer = new PrintWriter(new File(outputParent, "random.file"));
			writer.println("Placeholder");
			writer.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Activator.getLogger().info("RuM_ToyPlugin2 going to sleep");
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Activator.getLogger().info("RuM_ToyPlugin2 sleep done");
		return 0;
	}

}
