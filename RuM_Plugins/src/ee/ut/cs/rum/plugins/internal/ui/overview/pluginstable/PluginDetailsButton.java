package ee.ut.cs.rum.plugins.internal.ui.overview.pluginstable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.ui.details.PluginDetails;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class PluginDetailsButton extends Button {
	private static final long serialVersionUID = -7636382139932347297L;
	
	private RumController rumController;
	
	private Plugin plugin;
	private PluginsManagementUI pluginsManagementUI;

	public PluginDetailsButton(Composite parent, Plugin plugin, PluginsManagementUI pluginsManagementUI, RumController rumController) {
		super(parent, SWT.NONE);
		this.plugin = plugin;
		this.pluginsManagementUI = pluginsManagementUI;

		this.setText("Details");

		this.addSelectionListener(this.createSelectionListener());
	}

	private SelectionListener createSelectionListener() {
		return new SelectionListener() {
			private static final long serialVersionUID = 2256156491864328920L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Plugin plugin = ((PluginDetailsButton) arg0.getSource()).getPlugin();
				CTabItem cTabItem = null;

				//Checking if the tab is already open
				for (CTabItem c : pluginsManagementUI.getItems()) {
					if (c.getControl().getClass() == PluginDetails.class) {
						if (((PluginDetails)c.getControl()).getPlugin() == plugin) {
							cTabItem = c;
							pluginsManagementUI.setSelection(c);
						}
					}
				}

				if (cTabItem == null) {
					cTabItem = new CTabItem (pluginsManagementUI, SWT.CLOSE);
					cTabItem.setText ("Plugin " + plugin.getId().toString());
					cTabItem.setControl(new PluginDetails(pluginsManagementUI, plugin, rumController));
					pluginsManagementUI.setSelection(cTabItem);	
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		};
	}

	public Plugin getPlugin() {
		return plugin;
	}

}
