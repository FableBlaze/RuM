package ee.ut.cs.rum.workspace.ui.internal.newtask.pluginstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
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
		Plugin selectedPlugin = (Plugin) selection.getFirstElement();
		Bundle selectedPluginBundle = null;
		
		if (selectedPlugin!=null) {
			selectedPluginBundle = findSelectedPluginBundle(selectedPlugin);
			if (selectedPluginBundle==null) {
				selectedPluginBundle = installSelectedPluginBundle(selectedPlugin);
			}
		}
		
		newTaskComposite.getSelectedPluginInfo().updateSelectedPluginInfo(selectedPlugin);
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
			//temporaryBundle.stop();
			//temporaryBundle.uninstall();
		} catch (BundleException e) {
			Activator.getLogger().info("Failed loading plugin: " + selectedPlugin.toString());
		}
		return selectedPluginBundle;
	}


	private void updateNewTaskPluginConfigurationUi(Bundle selectedPluginBundle) {
		if (selectedPluginBundle==null || selectedPluginBundle.getRegisteredServices()==null) {
			for (Control child : newTaskComposite.getSelectedPluginConfigurationUi().getChildren()) {
				if (!child.isDisposed()) {
					child.dispose();
				}
			}
		} else {
			for (ServiceReference<?> iterable_element : selectedPluginBundle.getRegisteredServices()) {
				Activator.getLogger().info(iterable_element.toString());

				RumPluginFactory rpm = (RumPluginFactory) selectedPluginBundle.getBundleContext().getService(iterable_element);
				rpm.getRumPluginConfiguration().createConfigurationUi(newTaskComposite.getSelectedPluginConfigurationUi());
				newTaskComposite.getSelectedPluginConfigurationUi().setSize(newTaskComposite.getSelectedPluginConfigurationUi().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		}
	}
}
