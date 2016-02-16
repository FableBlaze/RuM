package ee.ut.cs.rum;

import javax.persistence.EntityManagerFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;
	private static EntityManagerFactory emf;
	private ServiceTracker<?, ?> serviceTracker;

	@SuppressWarnings("unchecked")
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		//We use activator to set up logging
		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");
		
		EmfTrackerCustomizer emfServiceTracker = new EmfTrackerCustomizer(context);
		serviceTracker = new ServiceTracker<Object, Object>(context, EntityManagerFactory.class.getName(), emfServiceTracker);
		serviceTracker.open();

		emf = (EntityManagerFactory) serviceTracker.getService();
		
		logger.info("RuM bundle started");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		serviceTracker.close();
		logger.info("RuM bundle stopped");
	}
	
	static BundleContext getContext() {
		return context;
	}
	
	//Rest of the bundle gets the logger trough activator
	public static Logger getLogger() {
		return logger;
	}
	
	//Rest of the bundle gets the entityManager trough activator
	public static EntityManagerFactory getEmf() {
		return emf;
	}
}
