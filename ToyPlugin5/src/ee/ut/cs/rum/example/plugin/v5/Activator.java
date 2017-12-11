package ee.ut.cs.rum.example.plugin.v5;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");
		logger.info("RuM_ToyPlugin5 started");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("RuM_ToyPlugin5 bundle stopped");
		Activator.context = null;
	}

	static BundleContext getContext() {
		return context;
	}
	
	public static Logger getLogger() {
		return logger;
	}
}
