package ee.ut.cs.rum.database.internal;

import javax.persistence.EntityManagerFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.ut.cs.rum.database.RumEmfService;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;
	private static EntityManagerFactory emf;

	private ServiceRegistration<?> dsfService;

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");
		logger.info("Starting RuM_database bundle");
		
		RumEmfService rumEmfService = new RumEmfServiceImpl(context);

		dsfService = context.registerService(RumEmfService.class.getName(), rumEmfService, null);
		emf = rumEmfService.getEmf("RuM");
		
		logger.info("RuM_database bundle started");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		if (dsfService != null) { 
			dsfService.unregister();  
		}
		logger.info("RuM_database bundle stopped");
		Activator.context = null;
	}

	static BundleContext getContext() {
		return context;
	}

	public static Logger getLogger() {
		return logger;
	}
	
	public static EntityManagerFactory getEmf() {
		return emf;
	}
}
