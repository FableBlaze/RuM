package ee.ut.cs.rum.administration.internal;

import java.util.Date;

import javax.persistence.EntityManagerFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.ut.cs.rum.database.EmfTrackerCustomizer;
import ee.ut.cs.rum.database.RumEmfService;
import ee.ut.cs.rum.database.domain.SystemParameter;
import ee.ut.cs.rum.database.domain.enums.SystemParameterName;
import ee.ut.cs.rum.database.util.SystemParameterAccess;

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
		
		initializeSystemParameters();
		
		logger.info("RuM_administration bundle started");
	}
	
	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("RuM_administration bundle stoppped");
		Activator.context = null;
	}
	
	private static void initializeSystemParameters() {
		Date date;
		
		SystemParameter systemParameter = new SystemParameter();
		systemParameter.setName(SystemParameterName.PLUGIN_PATH.toString());
		systemParameter.setDescription("Location of plugin jars");
		date = new Date();
		systemParameter.setCreatedBy("SYS (TODO)");
		systemParameter.setCreatedAt(date);
		systemParameter.setLastModifiedBy("SYS (TODO)");
		systemParameter.setLastModifiedAt(date);
		SystemParameterAccess.addSystemParameterDataToDb(systemParameter);
		
		systemParameter = new SystemParameter();
		systemParameter.setName(SystemParameterName.USER_FILE_PATH.toString());
		systemParameter.setDescription("Location of user uploaded files");
		date = new Date();
		systemParameter.setCreatedBy("SYS (TODO)");
		systemParameter.setCreatedAt(date);
		systemParameter.setLastModifiedBy("SYS (TODO)");
		systemParameter.setLastModifiedAt(date);
		SystemParameterAccess.addSystemParameterDataToDb(systemParameter);
		
		systemParameter = new SystemParameter();
		systemParameter.setName(SystemParameterName.TASK_RESULTS_ROOT.toString());
		systemParameter.setDescription("Location of task output folders");
		date = new Date();
		systemParameter.setCreatedBy("SYS (TODO)");
		systemParameter.setCreatedAt(date);
		systemParameter.setLastModifiedBy("SYS (TODO)");
		systemParameter.setLastModifiedAt(date);
		SystemParameterAccess.addSystemParameterDataToDb(systemParameter);
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
