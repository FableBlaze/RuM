package ee.ut.cs.rum.plugins.internal.util;

import javax.persistence.EntityManagerFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.ut.cs.rum.database.EmfTrackerCustomizer;
import ee.ut.cs.rum.database.RumEmfService;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;
	private ServiceTracker<?, ?> serviceTracker;
	private static EntityManagerFactory emf;

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");

		EmfTrackerCustomizer emfTrackerCustomizer = new EmfTrackerCustomizer(context);
		serviceTracker = new ServiceTracker<Object, Object>(context, RumEmfService.class.getName(), emfTrackerCustomizer);
		serviceTracker.open();

		RumEmfService rumEmfService = (RumEmfService) serviceTracker.getService();
		if (rumEmfService == null) {throw new Exception("Database service not found");}
		emf = rumEmfService.getEmf("RuM");
		if (emf == null) {throw new Exception("Database service not found");} 


		logger.info("RuM_plugins bundle started");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("RuM_database bundle stopped");
		Activator.context = null;
	}

	static BundleContext getContext() {
		return context;
	}

	public static EntityManagerFactory getEmf() {
		return emf;
	}
}
