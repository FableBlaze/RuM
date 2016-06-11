package ee.ut.cs.rum.internal;

import javax.persistence.EntityManagerFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.EmfTrackerCustomizer;
import ee.ut.cs.rum.database.RumEmfService;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;
	private static RumController rumController;
	private static EntityManagerFactory emf;
	private static ServiceTracker<?, ?> serviceTracker;

	@SuppressWarnings("unchecked")
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		//We use activator to set up logging
		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");
		
		rumController = new RumController();
		
		EmfTrackerCustomizer emfTrackerCustomizer = new EmfTrackerCustomizer(context);
		serviceTracker = new ServiceTracker<Object, Object>(context, RumEmfService.class.getName(), emfTrackerCustomizer);
		serviceTracker.open();

		RumEmfService rumEmfService = (RumEmfService) serviceTracker.getService();
		if (rumEmfService == null) {throw new Exception("Database service not found");}
		emf = rumEmfService.getEmf("RuM");
		if (emf == null) {throw new Exception("Database service not found");} 
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
	
	public static RumController getRumController() {
		return rumController;
	}
	
	//Rest of the bundle gets the entityManager trough activator
	public static EntityManagerFactory getEmf() {
		return emf;
	}
}
