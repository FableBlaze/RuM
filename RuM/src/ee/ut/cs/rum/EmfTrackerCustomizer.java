package ee.ut.cs.rum;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import ee.ut.cs.rum.database.RumEmfService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EmfTrackerCustomizer implements ServiceTrackerCustomizer {
	private final BundleContext context;

	public EmfTrackerCustomizer(BundleContext context) {
		this.context = context;
	}

	@Override
	public Object addingService(ServiceReference reference) {
		RumEmfService service = (RumEmfService) context.getService(reference);
		Activator.getLogger().info("Emf registered");
		return service;
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
		removedService(reference, service);
		addingService(reference);
		Activator.getLogger().info("Emf modified");
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		context.ungetService(reference);
		Activator.getLogger().info("Emf removed");
		//TODO: Handle removal of EntityManager
	}
}
