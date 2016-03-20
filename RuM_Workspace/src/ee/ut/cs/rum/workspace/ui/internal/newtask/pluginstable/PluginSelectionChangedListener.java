package ee.ut.cs.rum.workspace.ui.internal.newtask.pluginstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.interfaces.RumPluginFactory;
import ee.ut.cs.rum.workspace.internal.Activator;
import ee.ut.cs.rum.workspace.ui.internal.newtask.NewTaskComposite;

public class PluginSelectionChangedListener implements ISelectionChangedListener {
	private NewTaskComposite newTaskComposite;

	public PluginSelectionChangedListener(NewTaskComposite newTaskComposite) {
		this.newTaskComposite=newTaskComposite;
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		Plugin plugin = (Plugin) selection.getFirstElement();
		newTaskComposite.getSelectedPluginInfo().updateSelectedPluginInfo(plugin);
		//TODO: Check if plugin provided services are available
		if (plugin!=null && !isPluginInstalled(plugin)) {
			try {
				Bundle temporaryBundle = Activator.getContext().installBundle("file:///" + plugin.getFileLocation());
				temporaryBundle.start();
				//temporaryBundle.stop();
				//temporaryBundle.uninstall();
			} catch (BundleException e) {
				Activator.getLogger().info("Failed loading plugin: " + plugin.toString());
				newTaskComposite.getSelectedPluginInfo().updateSelectedPluginInfo(null);
			}
		}
	}

	//TODO: This check seems to already be done by the framework
	private boolean isPluginInstalled(Plugin plugin) {
		for (Bundle b : Activator.getContext().getBundles()) {
			if (b.getLocation().equals("file:///" + plugin.getFileLocation())) {
				//TODO: Proper service usage
				if (b.getRegisteredServices()!=null) {
					for (ServiceReference<?> iterable_element : b.getRegisteredServices()) {
						Activator.getLogger().info(iterable_element.toString());

						RumPluginFactory rpm = (RumPluginFactory) b.getBundleContext().getService(iterable_element);
						rpm.getRumPluginConfiguration().createConfigurationUi(newTaskComposite.getSelectedPluginConfigurationUi());
						newTaskComposite.getSelectedPluginConfigurationUi().setSize(newTaskComposite.getSelectedPluginConfigurationUi().computeSize(SWT.DEFAULT, SWT.DEFAULT));

					}
				}

				return true;
			}
		}
		return false;
	}

}
