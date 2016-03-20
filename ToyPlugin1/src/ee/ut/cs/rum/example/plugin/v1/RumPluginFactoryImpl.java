package ee.ut.cs.rum.example.plugin.v1;

import ee.ut.cs.rum.example.plugin.v1.factory.RumPluginConfigurationImpl;
import ee.ut.cs.rum.example.plugin.v1.factory.RumPluginResultsVisualizerImpl;
import ee.ut.cs.rum.example.plugin.v1.factory.RumPluginWorkerImpl;
import ee.ut.cs.rum.plugins.interfaces.RumPluginFactory;
import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginConfiguration;
import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginResultsVisualizer;
import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginWorker;

public class RumPluginFactoryImpl implements RumPluginFactory {

	@Override
	public RumPluginConfiguration createRumPluginConfiguration() {
		return new RumPluginConfigurationImpl();
	}

	@Override
	public RumPluginWorker createRumPluginWorker() {
		return new RumPluginWorkerImpl();
	}

	@Override
	public RumPluginResultsVisualizer createRumPluginResultsVisualizer() {
		return new RumPluginResultsVisualizerImpl();
	}

}
