package ee.ut.cs.rum.database.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.ut.cs.rum.database.RumEmfService;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;

	private ServiceRegistration<?> dsfService;

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");
		logger.info("Starting RuM_database bundle");
		
		

		dsfService = context.registerService(RumEmfService.class.getName(), new RumEmfServiceImpl(context), null);

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
}
