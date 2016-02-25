package ee.ut.cs.rum.plugins.internal.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import ee.ut.cs.rum.database.domain.Plugin;

public final class PluginsData {
	
	private PluginsData() {
	}
	
	public static List<Plugin> getPluginsDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select p from Plugin p order by p.id");
		List<Plugin> plugins = query.getResultList();
		return plugins;
	}
	
}
