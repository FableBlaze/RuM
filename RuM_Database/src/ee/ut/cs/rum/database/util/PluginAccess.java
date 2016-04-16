package ee.ut.cs.rum.database.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.internal.Activator;

public final class PluginAccess {
	
	private PluginAccess() {
	}
	
	public static List<Plugin> getPluginsDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select p from Plugin p order by p.id");
		@SuppressWarnings("unchecked")
		List<Plugin> plugins = query.getResultList();
		
		return plugins;
	}
	
	public static Plugin getPluginDataFromDb(Long pluginId) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.find(Plugin.class, pluginId);
		Plugin plugin = em.find(Plugin.class, pluginId);
		
		return plugin;
	}
}
