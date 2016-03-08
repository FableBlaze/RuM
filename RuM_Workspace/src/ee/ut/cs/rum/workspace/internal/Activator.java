package ee.ut.cs.rum.workspace.internal;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;
	private Bundle b;

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");
		
		logger.info("RuM_workspace bundle started");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("RuM_workspace bundle stoppped");
		Activator.context = null;
		b.uninstall();
	}

	static BundleContext getContext() {
		return context;
	}
}
