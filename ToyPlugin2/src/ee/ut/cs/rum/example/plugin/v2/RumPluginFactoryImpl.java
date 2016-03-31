package ee.ut.cs.rum.example.plugin.v2;

import ee.ut.cs.rum.example.plugin.v2.factory.RumPluginResultsVisualizerImpl;
import ee.ut.cs.rum.example.plugin.v2.factory.RumPluginWorkerImpl;
import ee.ut.cs.rum.plugins.interfaces.RumPluginFactory;
import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginResultsVisualizer;
import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginWorker;

public class RumPluginFactoryImpl implements RumPluginFactory {

	@Override
	public String getConfigurationParameters() {
		//TODO: Return parameters in JSON format
		return "Plugin2";
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
