package ee.ut.cs.rum.workspaces.internal.ui.task.newtask.pluginstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.custom.ScrolledComposite;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.deserializer.PluginInfoDeserializer;
import ee.ut.cs.rum.plugins.development.interfaces.RumPluginFactory;
import ee.ut.cs.rum.workspaces.internal.Activator;
import ee.ut.cs.rum.workspaces.internal.ui.task.newtask.NewTaskDetails;

public class PluginSelectionChangedListener implements ISelectionChangedListener {
	private NewTaskDetails newTaskDetails;

	public PluginSelectionChangedListener(NewTaskDetails newTaskDetails) {
		this.newTaskDetails=newTaskDetails;
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		Plugin selectedPlugin = (Plugin) selection.getFirstElement();
		Bundle selectedPluginBundle = null;

		if (selectedPlugin!=null) {
			selectedPluginBundle = findSelectedPluginBundle(selectedPlugin);
			if (selectedPluginBundle==null) {
				selectedPluginBundle = installSelectedPluginBundle(selectedPlugin);
			}
		}
		
		if (selectedPluginBundle==null) {
			newTaskDetails.getFooterButtonsComposite().getStartButton().setEnabled(false);
		} else {
			newTaskDetails.getFooterButtonsComposite().getStartButton().setEnabled(true);
		}

		newTaskDetails.getSelectedPluginInfo().updateSelectedPluginInfo(selectedPlugin);
		updateNewTaskPluginConfigurationUi(selectedPluginBundle);
	}


	private Bundle findSelectedPluginBundle(Plugin selectedPlugin) {
		for (Bundle bundle : Activator.getContext().getBundles()) {
			if (bundle.getLocation().equals("file:///" + selectedPlugin.getFileLocation())) {
				return bundle;
			}
		}
		return null;
	}

	private Bundle installSelectedPluginBundle(Plugin selectedPlugin) {
		Bundle selectedPluginBundle = null;
		try {
			selectedPluginBundle = Activator.getContext().installBundle("file:///" + selectedPlugin.getFileLocation());
			selectedPluginBundle.start();
		} catch (BundleException e) {
			Activator.getLogger().info("Failed loading plugin: " + selectedPlugin.toString());
		}
		return selectedPluginBundle;
	}


	private void updateNewTaskPluginConfigurationUi(Bundle selectedPluginBundle) {
		ScrolledComposite selectedPluginConfigurationUi = newTaskDetails.getSelectedPluginConfigurationUi();

		if (selectedPluginConfigurationUi.getContent()!=null && !selectedPluginConfigurationUi.getContent().isDisposed()) {
			selectedPluginConfigurationUi.getContent().dispose();
		}

		if (selectedPluginBundle!=null && selectedPluginBundle.getRegisteredServices()!=null) {
			for (ServiceReference<?> serviceReference : selectedPluginBundle.getRegisteredServices()) {
				if (implementsRumPluginFactory(serviceReference)) {
					RumPluginFactory rumPluginFactory = (RumPluginFactory) selectedPluginBundle.getBundleContext().getService(serviceReference);
					String pluginInfoJson = rumPluginFactory.getPluginInfoJSON();
					
					GsonBuilder gsonBuilder = new GsonBuilder();
					gsonBuilder.registerTypeAdapter(PluginInfo.class, new PluginInfoDeserializer());
					Gson gson = gsonBuilder.create();
					PluginInfo pluginInfo = gson.fromJson(pluginInfoJson, PluginInfo.class);
					
//					PluginConfigurationUi pluginConfigurationUi = new PluginConfigurationUi(selectedPluginConfigurationUi, pluginInfo);
//					selectedPluginConfigurationUi.setContent(pluginConfigurationUi);
//					pluginConfigurationUi.setSize(pluginConfigurationUi.computeSize(SWT.DEFAULT, SWT.DEFAULT));
					
					//TODO: Update configuration on plugin change
					//newTaskDialogShell.setRumPluginConfiguration(configurationUI);
				}
			}
		}
	}

	private boolean implementsRumPluginFactory(ServiceReference<?> serviceReference) {
		String[] objectClasses = (String[])serviceReference.getProperty("objectClass");
		for (String objectClass : objectClasses) {
			if (objectClass.equals(RumPluginFactory.class.getName())) {
				return true;
			}
		}
		return false;
	}
}
