package ee.ut.cs.rum;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DatabaseServiceTrackerCustomizer implements ServiceTrackerCustomizer {
	private final BundleContext context;

	public DatabaseServiceTrackerCustomizer(BundleContext context) {
		this.context = context;
	}

	@Override
	public Object addingService(ServiceReference reference) {
		DataSource service = (DataSource) context.getService(reference);
		Activator.getLogger().info("Datasource registered");
		return service;
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
		removedService(reference, service);
		addingService(reference);
		Activator.getLogger().info("Database modified");
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		context.ungetService(reference);
		Activator.getLogger().info("Database removed");
		//TODO: Handle removal of database
	}
}
