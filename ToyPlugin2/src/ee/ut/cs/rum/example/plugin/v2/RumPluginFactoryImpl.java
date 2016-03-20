package ee.ut.cs.rum.example.plugin.v2;

import ee.ut.cs.rum.example.plugin.v2.factory.RumPluginConfigurationImpl;
import ee.ut.cs.rum.example.plugin.v2.factory.RumPluginResultsVisualizerImpl;
import ee.ut.cs.rum.example.plugin.v2.factory.RumPluginWorkerImpl;
import ee.ut.cs.rum.plugins.interfaces.RumPluginFactory;
import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginConfiguration;
import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginResultsVisualizer;
import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginWorker;

public class RumPluginFactoryImpl implements RumPluginFactory {

	@Override
	public RumPluginConfiguration getRumPluginConfiguration() {
		return new RumPluginConfigurationImpl();
	}

	@Override
	public RumPluginWorker getRumPluginWorker() {
		return new RumPluginWorkerImpl();
	}

	@Override
	public RumPluginResultsVisualizer getRumPluginResultsVisualizer() {
		return new RumPluginResultsVisualizerImpl();
	}

}
