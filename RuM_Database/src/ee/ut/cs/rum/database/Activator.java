package ee.ut.cs.rum.database;

import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;

	private ServiceRegistration<?> dsfService;

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");
		logger.info("Starting RuM_database bundle");

		HashMap<String, String> props = new HashMap<String, String>(); 
		props.put("javax.persistence.jdbc.url", "jdbc:postgresql://127.0.0.1:5432/RuM"); 
		props.put("javax.persistence.jdbc.user", "postgres"); 
		props.put("javax.persistence.jdbc.password", "postgres");
		props.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");

		ServiceReference[] refs = context.getServiceReferences(EntityManagerFactoryBuilder.class.getName(),
				"(osgi.unit.name=RuM)");
		//TODO: Add error handling
		EntityManagerFactoryBuilder emfb = (EntityManagerFactoryBuilder)context.getService(refs[0]);
		EntityManagerFactory emf = emfb.createEntityManagerFactory(props);
		EntityManager em = emf.createEntityManager();

		dsfService = context.registerService(EntityManager.class.getName(), em, null);

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
