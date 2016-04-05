package ee.ut.cs.rum.workspaces.internal.ui.task.newtask.pluginstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.ui.PluginConfigurationUi;
import ee.ut.cs.rum.workspaces.internal.ui.task.newtask.NewTaskDetails;
import ee.ut.cs.rum.workspaces.internal.util.PluginUtils;

public class PluginSelectionChangedListener implements ISelectionChangedListener {
	private NewTaskDetails newTaskDetails;

	public PluginSelectionChangedListener(NewTaskDetails newTaskDetails) {
		this.newTaskDetails=newTaskDetails;
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		Plugin selectedPlugin = (Plugin) selection.getFirstElement();

		if (selectedPlugin!=null) {
			newTaskDetails.getSelectedPluginInfo().updateSelectedPluginInfo(selectedPlugin);

			ScrolledComposite selectedPluginConfigurationUi = newTaskDetails.getSelectedPluginConfigurationUi();

			if (selectedPluginConfigurationUi.getContent()!=null && !selectedPluginConfigurationUi.getContent().isDisposed()) {
				selectedPluginConfigurationUi.getContent().dispose();
			}

			PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(selectedPlugin);

			PluginConfigurationUi pluginConfigurationUi = new PluginConfigurationUi(selectedPluginConfigurationUi, pluginInfo);
			selectedPluginConfigurationUi.setContent(pluginConfigurationUi);
			selectedPluginConfigurationUi.setSize(selectedPluginConfigurationUi.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			selectedPluginConfigurationUi.getParent().setSize(selectedPluginConfigurationUi.getParent().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			
			newTaskDetails.getFooterButtonsComposite().setEnabled(true);
		} else {
			newTaskDetails.getFooterButtonsComposite().setEnabled(false);
		}
	}
}
