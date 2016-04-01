package ee.ut.cs.rum.workspaces.internal.ui.task.newtask.pluginstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.interfaces.RumPluginFactory;
import ee.ut.cs.rum.workspaces.internal.Activator;
import ee.ut.cs.rum.workspaces.internal.ui.PluginContentComposite;
import ee.ut.cs.rum.workspaces.internal.ui.task.newtask.dialog.NewTaskDialogShell;

public class PluginSelectionChangedListener implements ISelectionChangedListener {
	private NewTaskDialogShell newTaskDialogShell;

	public PluginSelectionChangedListener(NewTaskDialogShell newTaskDialogShell) {
		this.newTaskDialogShell=newTaskDialogShell;
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
			newTaskDialogShell.getNewTaskDialog().getFooterButtonsComposite().getStartButton().setEnabled(false);
		} else {
			newTaskDialogShell.getNewTaskDialog().getFooterButtonsComposite().getStartButton().setEnabled(true);
		}

		newTaskDialogShell.getNewTaskDialog().getSelectedPluginInfo().updateSelectedPluginInfo(selectedPlugin);
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
		ScrolledComposite selectedPluginConfigurationUi = newTaskDialogShell.getNewTaskDialog().getSelectedPluginConfigurationUi();

		if (selectedPluginConfigurationUi.getContent()!=null && !selectedPluginConfigurationUi.getContent().isDisposed()) {
			selectedPluginConfigurationUi.getContent().dispose();
		}

		if (selectedPluginBundle!=null && selectedPluginBundle.getRegisteredServices()!=null) {
			for (ServiceReference<?> serviceReference : selectedPluginBundle.getRegisteredServices()) {
				if (implementsRumPluginFactory(serviceReference)) {
					PluginContentComposite content = new PluginContentComposite(selectedPluginConfigurationUi, SWT.NONE);
					content.setLayout(new GridLayout());
					
					RumPluginFactory rumPluginFactory = (RumPluginFactory) selectedPluginBundle.getBundleContext().getService(serviceReference);
					String configurationParameters = rumPluginFactory.getPluginInfoJSON();
					Label configurationUI = new Label(content, SWT.NONE);
					configurationUI.setText(configurationParameters);
					content.setSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
					selectedPluginConfigurationUi.setContent(content);
					
					newTaskDialogShell.setRumPluginConfiguration(configurationUI);
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
