package ee.ut.cs.rum.plugins.internal.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.Activator;
import ee.ut.cs.rum.plugins.internal.ui.OverviewTabContents;
import ee.ut.cs.rum.plugins.internal.ui.PluginsTableViewer;

public final class PluginsData {
	
	private PluginsData() {
	}
	
	public static List<Plugin> getPluginsDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select p from Plugin p order by p.id");
		@SuppressWarnings("unchecked")
		List<Plugin> plugins = query.getResultList();
		
		return plugins;
	}
	
	public static void addPluginDataToDb(Plugin plugin, OverviewTabContents overviewTabContents) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(plugin);
		em.getTransaction().commit();
		em.close();
		
		PluginsTableViewer pluginsTableViewer = overviewTabContents.getPluginsTableViewer();
		List<Plugin> plugins = getPluginsDataFromDb();
		pluginsTableViewer.refresh();
		
		overviewTabContents.getPluginsOverview().getNumberOfPluginsLable().setText(Integer.toString(plugins.size()));
		
		Activator.getLogger().info("Added plugin to db: " + plugin.toString());
	}
	
}
