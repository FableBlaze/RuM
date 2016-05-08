package ee.ut.cs.rum.workspace.internal.ui.task.newtask.pluginstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.workspace.internal.ui.task.PluginInfoComposite;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskDetails;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.plugins.configuration.util.PluginUtils;

public class PluginSelectionChangedListener implements ISelectionChangedListener {
	private NewTaskDetails newTaskDetails;

	public PluginSelectionChangedListener(NewTaskDetails newTaskDetails) {
		this.newTaskDetails=newTaskDetails;
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		Plugin selectedPlugin=null;
		
		if (event!=null) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			selectedPlugin = (Plugin) selection.getFirstElement();			
		}

		if (selectedPlugin!=null) {
			PluginInfoComposite selectedPluginInfo = newTaskDetails.getPluginInfoComposite();
			selectedPluginInfo.updateSelectedPluginInfo(selectedPlugin);
			//selectedPluginInfo.getContent().setSize(selectedPluginInfo.getContent().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			selectedPluginInfo.setSize(selectedPluginInfo.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			
			ScrolledComposite scrolledPluginConfigurationComposite = newTaskDetails.getScrolledPluginConfigurationComposite();

			if (scrolledPluginConfigurationComposite.getContent()!=null && !scrolledPluginConfigurationComposite.getContent().isDisposed()) {
				scrolledPluginConfigurationComposite.getContent().dispose();
			}

			PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(selectedPlugin);
			
			PluginConfigurationComposite pluginConfigurationComposite = new PluginConfigurationComposite(scrolledPluginConfigurationComposite, pluginInfo, newTaskDetails.getProjectTabFolder().getProject().getId());
			scrolledPluginConfigurationComposite.setContent(pluginConfigurationComposite);
			pluginConfigurationComposite.setSize(pluginConfigurationComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			
			newTaskDetails.layout();
			
			newTaskDetails.getFooterButtonsComposite().setEnabled(true);
		} else {
			newTaskDetails.getFooterButtonsComposite().setEnabled(false);
		}
	}
}
