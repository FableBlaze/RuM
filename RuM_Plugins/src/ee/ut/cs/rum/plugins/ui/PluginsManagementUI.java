package ee.ut.cs.rum.plugins.ui;

import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.plugins.internal.ui.PluginsTable;

public final class PluginsManagementUI {
	private PluginsManagementUI() {
	}
	
	public static void createPluginsManagementUI(Composite parent) {
		PluginsTable.createPluginsTable(parent);
		
	}
}
