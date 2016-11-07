package ee.ut.cs.rum.database.internal;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

import ee.ut.cs.rum.database.RumEmfService;

public class RumEmfServiceImpl implements RumEmfService {
	private EntityManagerFactory emf;
	
	public RumEmfServiceImpl() throws InvalidSyntaxException {
		BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		HashMap<String, String> props = new HashMap<String, String>(); 
		props.put("javax.persistence.jdbc.url", "jdbc:postgresql://127.0.0.1:5432/RuM_dev"); 
		props.put("javax.persistence.jdbc.user", "postgres"); 
		props.put("javax.persistence.jdbc.password", "postgres");
		props.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");

		@SuppressWarnings("rawtypes")
		ServiceReference[] refs = context.getServiceReferences(EntityManagerFactoryBuilder.class.getName(),
				"(osgi.unit.name=RuM)");
		//TODO: Add error handling
		@SuppressWarnings("unchecked")
		EntityManagerFactoryBuilder emfb = (EntityManagerFactoryBuilder)context.getService(refs[0]);
		emf = emfb.createEntityManagerFactory(props);
	}
	
	public EntityManagerFactory getEmf(String token) {
		if (token.equals("RuM")) {
			return emf;
		} else {
			return null;
		}
		
	}
}
