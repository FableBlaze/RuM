package ee.ut.cs.rum.administration.internal;

import javax.persistence.EntityManagerFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.ut.cs.rum.administration.internal.util.SystemParametersData;
import ee.ut.cs.rum.database.EmfTrackerCustomizer;
import ee.ut.cs.rum.database.RumEmfService;
import ee.ut.cs.rum.database.domain.SystemParameter;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;
	private static EntityManagerFactory emf;

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");
		
		EmfTrackerCustomizer emfTrackerCustomizer = new EmfTrackerCustomizer(context);
		@SuppressWarnings("unchecked")
		ServiceTracker<?, ?> serviceTracker = new ServiceTracker<Object, Object>(context, RumEmfService.class.getName(), emfTrackerCustomizer);
		serviceTracker.open();

		RumEmfService rumEmfService = (RumEmfService) serviceTracker.getService();
		if (rumEmfService == null) {throw new Exception("Database service not found");}
		emf = rumEmfService.getEmf("RuM");
		if (emf == null) {throw new Exception("Database service not found");}
		
		SystemParametersData.initializeSystemParameters();
		
		logger.info("RuM_administration bundle started");
	}
	
	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("RuM_administration bundle stoppped");
		Activator.context = null;
	}

	public static BundleContext getContext() {
		return context;
	}

	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	public static Logger getLogger() {
		return logger;
	}
}
