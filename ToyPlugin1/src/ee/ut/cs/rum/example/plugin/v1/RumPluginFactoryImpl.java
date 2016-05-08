package ee.ut.cs.rum.example.plugin.v1;

import ee.ut.cs.rum.example.plugin.v1.factory.RumPluginWorkerImpl;
import ee.ut.cs.rum.example.plugin.v1.utils.PluginConfiguration;
import ee.ut.cs.rum.plugins.development.interfaces.RumPluginFactory;
import ee.ut.cs.rum.plugins.development.interfaces.factory.RumPluginWorker;

public class RumPluginFactoryImpl implements RumPluginFactory {

	@Override
	public String getPluginInfoJSON() {
		return PluginConfiguration.generatePluginInfoJSON();
	}

	@Override
	public RumPluginWorker createRumPluginWorker() {
		return new RumPluginWorkerImpl();
	}

}
