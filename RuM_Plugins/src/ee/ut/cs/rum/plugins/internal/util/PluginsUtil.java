package ee.ut.cs.rum.plugins.internal.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.util.PluginAccess;
import ee.ut.cs.rum.plugins.internal.Activator;
import ee.ut.cs.rum.plugins.internal.ui.overview.OverviewTabContents;

public final class PluginsUtil {
	
	private PluginsUtil() {
	}
	
	public static void addPluginDataToDb(Plugin plugin, OverviewTabContents overviewTabContents) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(plugin);
		em.getTransaction().commit();
		em.close();
		
		List<Plugin> plugins = PluginAccess.getPluginsDataFromDb();
		overviewTabContents.getPluginsTableComposite().getPluginsTableViewer().setInput(plugins);
		overviewTabContents.getPluginsOverview().getNumberOfPluginsLable().setText(Integer.toString(plugins.size()));
		
		Activator.getLogger().info("Added plugin: " + plugin.toString());
	}
	
}
