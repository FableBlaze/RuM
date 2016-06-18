package ee.ut.cs.rum.scheduler.internal;

import javax.persistence.EntityManagerFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.EmfTrackerCustomizer;
import ee.ut.cs.rum.database.RumEmfService;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;
	private static EntityManagerFactory emf;
	private static Scheduler scheduler;
	private static RumController rumController;

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

		SchedulerFactory schedulerFactory = new StdSchedulerFactory("quartz.properties");
		scheduler = schedulerFactory.getScheduler();
		scheduler.start();

		logger.info("RuM_scheduler bundle started");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("RuM_scheduler bundle stopped");
		if (scheduler!=null && !scheduler.isShutdown()) {
			scheduler.shutdown();
		}
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

	public static Scheduler getScheduler() {
		return scheduler;
	}

	public static void setRumController(RumController rumController) {
		if (Activator.rumController==null) {
			Activator.rumController = rumController;
		}
	}
	
	public static RumController getRumController() {
		return rumController;
	}
}
