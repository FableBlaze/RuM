package ee.ut.cs.rum.plugins.internal.ui.overview.pluginstable;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.ui.details.PluginDetails;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class PluginRowDoubleClickListener implements IDoubleClickListener {

	private PluginsManagementUI pluginsManagementUI;
	private RumController rumController;

	public PluginRowDoubleClickListener(PluginsManagementUI pluginsManagementUI, RumController rumController) {
		this.pluginsManagementUI = pluginsManagementUI;
		this.rumController=rumController;
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		Plugin selectedPlugin = null;
		CTabItem cTabItem = null;
		
		if (event!=null) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			selectedPlugin = (Plugin)selection.getFirstElement();
			
			//Checking if the tab is already open
			for (CTabItem c : pluginsManagementUI.getItems()) {
				if (c.getControl().getClass() == PluginDetails.class) {
					if (((PluginDetails)c.getControl()).getPlugin() == selectedPlugin) {
						cTabItem = c;
						pluginsManagementUI.setSelection(c);
					}
				}
			}

			if (cTabItem == null) {
				cTabItem = new CTabItem (pluginsManagementUI, SWT.CLOSE);
				cTabItem.setText ("Plugin " + selectedPlugin.getId().toString());
				cTabItem.setControl(new PluginDetails(pluginsManagementUI, selectedPlugin, rumController));
				pluginsManagementUI.setSelection(cTabItem);	
			}
		}
	}
}
