package ee.ut.cs.rum.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;

	private Connection connection;
	private ServiceTracker<?, ?> packageAdminTracker;
	private ServiceRegistration<?> dsfService;

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");
		logger.info("Starting RuM_database bundle");

		Filter filter = context.createFilter(
				"(&(objectClass=" + DataSourceFactory.class.getName() + 
				")(" + DataSourceFactory.OSGI_JDBC_DRIVER_CLASS + "=org.postgresql.Driver))");
		packageAdminTracker = new ServiceTracker<Object, Object>(context, filter, null); 
		packageAdminTracker.open();

		DataSourceFactory dsf = (DataSourceFactory) packageAdminTracker.getService();

		Properties props = new Properties(); 
		props.put(DataSourceFactory.JDBC_URL, "jdbc:postgresql://127.0.0.1:5432/RuM"); 
		props.put(DataSourceFactory.JDBC_USER, "postgres"); 
		props.put(DataSourceFactory.JDBC_PASSWORD, "postgres");

		DataSource dataSource = dsf.createDataSource(props);

		try {
			connection = dataSource.getConnection();
			connection.getMetaData();
			connection.close();
		} catch (SQLException sqlEx) { 
			logger.info("Database connection failed: " + sqlEx); 
		}

		if (connection != null) {
			dsfService = context.registerService(DataSource.class.getName(), dataSource, null);
		}
		logger.info("RuM_database bundle started");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("RuM_database bundle stopped");
		packageAdminTracker.close(); 
		if (dsfService != null) { 
			dsfService.unregister(); 
		}
		Activator.context = null;
	}

	static BundleContext getContext() {
		return context;
	}
}
