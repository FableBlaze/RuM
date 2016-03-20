package ee.ut.cs.rum.workspace.ui.internal.newtask.pluginstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.interfaces.RumPluginFactory;
import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginConfiguration;
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
		} catch (BundleException e) {
			Activator.getLogger().info("Failed loading plugin: " + selectedPlugin.toString());
		}
		return selectedPluginBundle;
	}


	private void updateNewTaskPluginConfigurationUi(Bundle selectedPluginBundle) {
		ScrolledComposite selectedPluginConfigurationUi = newTaskComposite.getSelectedPluginConfigurationUi();

		if (selectedPluginConfigurationUi.getContent()!=null && !selectedPluginConfigurationUi.getContent().isDisposed()) {
			selectedPluginConfigurationUi.getContent().dispose();
		}

		if (selectedPluginBundle!=null && selectedPluginBundle.getRegisteredServices()!=null) {
			for (ServiceReference<?> serviceReference : selectedPluginBundle.getRegisteredServices()) {
				if (implementsRumPluginFactory(serviceReference)) {
					Composite content = new Composite(selectedPluginConfigurationUi, SWT.NONE);
					content.setLayout(new GridLayout());
					
					RumPluginFactory rumPluginFactory = (RumPluginFactory) selectedPluginBundle.getBundleContext().getService(serviceReference);
					RumPluginConfiguration rumPluginConfiguration = rumPluginFactory.getRumPluginConfiguration();
					rumPluginConfiguration.createConfigurationUi(content);
					content.setSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
					selectedPluginConfigurationUi.setContent(content);
					
					newTaskComposite.setRumPluginConfiguration(rumPluginConfiguration);
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
