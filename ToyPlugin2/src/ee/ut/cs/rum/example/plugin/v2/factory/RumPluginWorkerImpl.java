package ee.ut.cs.rum.example.plugin.v2.factory;

import ee.ut.cs.rum.example.plugin.v2.Activator;
import ee.ut.cs.rum.plugins.development.interfaces.factory.RumPluginWorker;

public class RumPluginWorkerImpl implements RumPluginWorker {

	@Override
	public Object runWork(String configuration) {
		// TODO Do something with parameters
		Activator.getLogger().info("RuM_ToyPlugin2 configuration: " + configuration);
		
		Activator.getLogger().info("RuM_ToyPlugin2 going to sleep");
		try {
			Thread.sleep(65L * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Activator.getLogger().info("RuM_ToyPlugin2 sleep done");
		return configuration.length();
	}

}
