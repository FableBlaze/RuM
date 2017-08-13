package ee.ut.cs.rum.plugins.internal.ui.dialog;

import java.io.File;
import org.eclipse.rap.fileupload.DiskFileUploadReceiver;
import org.eclipse.rap.fileupload.FileUploadEvent;
import org.eclipse.rap.fileupload.FileUploadListener;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceException;
import org.osgi.framework.ServiceReference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.deserializer.PluginInfoDeserializer;
import ee.ut.cs.rum.plugins.development.interfaces.RumPluginFactory;
import ee.ut.cs.rum.plugins.internal.Activator;

public class PluginUploadListener implements FileUploadListener {
	private DiskFileUploadReceiver receiver;
	private PluginUploadDialog pluginUploadDialog;

	private Gson gson;

	public PluginUploadListener(DiskFileUploadReceiver receiver, PluginUploadDialog pluginUploadDialog) {
		this.receiver=receiver;
		this.pluginUploadDialog=pluginUploadDialog;

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(PluginInfo.class, new PluginInfoDeserializer());
		gson = gsonBuilder.create();
	}

	@Override
	public void uploadFailed(FileUploadEvent arg0) {}
	@Override
	public void uploadProgress(FileUploadEvent arg0) {}

	@Override
	public void uploadFinished(FileUploadEvent arg0) {
		Bundle temporaryBundle = null;
		String pluginInfoJson = null;
		PluginInfo pluginInfo = null;

		File temporaryFile = receiver.getTargetFiles()[receiver.getTargetFiles().length-1];
		pluginUploadDialog.setTemporaryFile(temporaryFile);
		Activator.getLogger().info("Uploaded file: " + temporaryFile.getAbsolutePath());

		try {
			temporaryBundle = Activator.getContext().installBundle("file:///" + temporaryFile.getAbsolutePath());
			Activator.getLogger().info("Temporary plugin loaded");
			temporaryBundle.start();
			pluginInfoJson = getPluginInfoFromBundle(temporaryBundle);
			temporaryBundle.stop();
			Activator.getLogger().info("Temporary plugin initial start/stop done");
			pluginInfo = gson.fromJson(pluginInfoJson, PluginInfo.class);
			Activator.getLogger().info("Plugin info deserialized");

			//TODO: Check for duplicates
			Plugin temporaryPlugin = temporaryBundleToPlugin(temporaryBundle);
			temporaryPlugin.setPluginName(pluginInfo.getName());
			temporaryPlugin.setPluginDescription(pluginInfo.getDescription());
			temporaryPlugin.setPluginInfo(gson.toJson(pluginInfo));

			temporaryPlugin.setOriginalFilename(temporaryFile.getName());
			temporaryPlugin.setEnabled(true);
			pluginUploadDialog.setTemporaryPlugin(temporaryPlugin, "");
		} catch (BundleException e) {
			Activator.getLogger().info("Invalid bundle" + e.toString());
			pluginUploadDialog.setTemporaryPlugin(null, "Invalid plugin bundle");
		} catch (ServiceException e) {
			Activator.getLogger().info("Error getting plugin info JSON from RumPluginFactory service: " + e.toString());
			pluginUploadDialog.setTemporaryPlugin(null, e.getMessage());
		} catch (JsonSyntaxException e) {
			Activator.getLogger().info("Plugin info JSON malformed: " + e.toString());
			pluginUploadDialog.setTemporaryPlugin(null, "Malformed plugin info JSON");
		} catch (JsonParseException e) {
			Activator.getLogger().info("Deserializing plugin info JSON failed: " + e.toString());
			pluginUploadDialog.setTemporaryPlugin(null, e.getMessage());
		} catch (PluginManifestException e) {
			Activator.getLogger().info("Invalid plugin manifest: " + e.toString());
			pluginUploadDialog.setTemporaryPlugin(null, "Invalid plugin manifest");
		} catch (Exception e) {
			Activator.getLogger().info("General error: " + e.toString());
			pluginUploadDialog.setTemporaryPlugin(null, "General error when loading plugin");
		} finally {
			if (temporaryBundle!=null) {
				try {
					temporaryBundle.uninstall();
					Activator.getLogger().error("Temporary plugin uninstalled");
				} catch (BundleException e) {
					Activator.getLogger().error("Temporary plugin uninstalling failed");
				}
			}
		}
	}

	private String getPluginInfoFromBundle (Bundle temporaryBundle) throws ServiceException {
		if (temporaryBundle.getRegisteredServices()!=null) {
			for (ServiceReference<?> serviceReference : temporaryBundle.getRegisteredServices()) {
				String[] objectClasses = (String[])serviceReference.getProperty("objectClass");
				for (String objectClass : objectClasses) {
					if (objectClass.equals(RumPluginFactory.class.getName())) {
						RumPluginFactory rumPluginFactory = (RumPluginFactory) temporaryBundle.getBundleContext().getService(serviceReference);
						return rumPluginFactory.getPluginInfoJSON();
					}
				}	
			}
		}
		throw new ServiceException("RumPluginFactory service error");
	}

	private Plugin temporaryBundleToPlugin(Bundle temporaryBundle) throws PluginManifestException {
		Plugin temporaryPlugin = new Plugin();
		temporaryPlugin.setBundleSymbolicName(getValueFromManifest("Bundle-SymbolicName", temporaryBundle));
		temporaryPlugin.setBundleVersion(getValueFromManifest("Bundle-Version", temporaryBundle));
		temporaryPlugin.setBundleName(getValueFromManifest("Bundle-Name", temporaryBundle));
		temporaryPlugin.setBundleVendor(getValueFromManifest("Bundle-Vendor", temporaryBundle));
		temporaryPlugin.setBundleDescription(getValueFromManifest("Bundle-Description", temporaryBundle));
		temporaryPlugin.setBundleActivator(getValueFromManifest("Bundle-Activator", temporaryBundle));
		temporaryPlugin.setBundleImportPackage(getValueFromManifest("Import-Package", temporaryBundle));
		return temporaryPlugin;
	}

	private String getValueFromManifest(String key, Bundle temporaryBundle) throws PluginManifestException {
		String value = temporaryBundle.getHeaders().get(key);
		if (value!=null && !value.equals("")) {
			return value;
		} else {
			throw new PluginManifestException();			
		}
	}
}
