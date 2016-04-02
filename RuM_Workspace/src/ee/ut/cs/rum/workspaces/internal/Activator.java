package ee.ut.cs.rum.workspaces.internal;

import javax.persistence.EntityManagerFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import ee.ut.cs.rum.database.EmfTrackerCustomizer;
import ee.ut.cs.rum.database.RumEmfService;
import ee.ut.cs.rum.plugins.description.PluginInfo;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameter;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameterSelectionItem;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameterString;
import ee.ut.cs.rum.plugins.description.parameter.PluginParameterType;

public class Activator implements BundleActivator {
	private static BundleContext context;
	private static Logger logger;
	private static EntityManagerFactory emf;

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		logger = LoggerFactory.getLogger("ee.ut.cs.rum.virgoConsole");
		
		EmfTrackerCustomizer emfTrackerCustomizer = new EmfTrackerCustomizer(context);
		@SuppressWarnings("unchecked")
		ServiceTracker<?, ?> serviceTracker = new ServiceTracker<Object, Object>(context, RumEmfService.class.getName(), emfTrackerCustomizer);
		serviceTracker.open();

		RumEmfService rumEmfService = (RumEmfService) serviceTracker.getService();
		if (rumEmfService == null) {throw new Exception("Database service not found");}
		emf = rumEmfService.getEmf("RuM");
		if (emf == null) {throw new Exception("Database service not found");} 
		
		logger.info("RuM_workspace bundle started");
		
		//TODO: Remove gson testing code
		
		PluginParameterSelectionItem si = new PluginParameterSelectionItem();
		si.setDescription("First selection");
		si.setDisplayName("visiName");
		si.setInternalName("thecode");
		PluginParameterSelectionItem si2 = new PluginParameterSelectionItem();
		si2.setDescription("Second selection");
		si2.setDisplayName("visiName2");
		si2.setInternalName("thecode2");
		
		PluginParameterSelection ps1 = new PluginParameterSelection();
		
		ps1.setDescription("select params");
		ps1.setDisplayName("selName");
		ps1.setInternalName("selnInternale");
		ps1.setMultiSelection(false);
		ps1.setRequired(true);
		
		PluginParameterSelectionItem[] psi = {si,si2};
		ps1.setSelectionItems(psi);
		
		PluginParameter[] parameters = {ps1};
		PluginInfo pi = new PluginInfo();
		pi.setDescription("plugindescr");
		pi.setName("pluginName");
		pi.setParameters(parameters);
		Gson gson = new Gson();
		String json = gson.toJson(pi);
		Activator.getLogger().info(json);
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("RuM_workspace bundle stoppped");
		Activator.context = null;
	}

	public static BundleContext getContext() {
		return context;
	}

	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	public static Logger getLogger() {
		return logger;
	}
}
