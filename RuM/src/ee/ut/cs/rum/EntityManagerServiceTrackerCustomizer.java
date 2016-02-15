package ee.ut.cs.rum;

import javax.persistence.EntityManager;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EntityManagerServiceTrackerCustomizer implements ServiceTrackerCustomizer {
	private final BundleContext context;

	public EntityManagerServiceTrackerCustomizer(BundleContext context) {
		this.context = context;
	}

	@Override
	public Object addingService(ServiceReference reference) {
		EntityManager service = (EntityManager) context.getService(reference);
		Activator.getLogger().info("EntityManager registered");
		return service;
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
		removedService(reference, service);
		addingService(reference);
		Activator.getLogger().info("EntityManager modified");
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		context.ungetService(reference);
		Activator.getLogger().info("EntityManager removed");
		//TODO: Handle removal of EntityManager
	}
}
