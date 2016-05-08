package ee.ut.cs.rum.plugins.development.interfaces;

import ee.ut.cs.rum.plugins.development.interfaces.factory.RumPluginWorker;

public interface RumPluginFactory {
	public String getPluginInfoJSON();
	public RumPluginWorker createRumPluginWorker();
}
