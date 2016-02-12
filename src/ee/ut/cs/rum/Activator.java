package ee.ut.cs.rum;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		//We use activator to set up logging
		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");
		logger.info("RuM bundle started");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		logger.info("RuM bundle stopped");
	}
	
	static BundleContext getContext() {
		return context;
	}
	
	//Rest of the application gets the logger instance trough activator
	public static Logger getLogger() {
		return logger;
	}
}
