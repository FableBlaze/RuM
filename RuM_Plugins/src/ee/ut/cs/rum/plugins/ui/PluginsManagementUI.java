package ee.ut.cs.rum.plugins.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.ui.util.PluginsOverview;
import ee.ut.cs.rum.plugins.internal.ui.util.PluginsTable;
import ee.ut.cs.rum.plugins.internal.util.PluginsData;

public final class PluginsManagementUI {
	
	private PluginsManagementUI() {
	}

	public static void createPluginsManagementUI(Composite parent) {
		List<Plugin> plugins = PluginsData.getPluginsDataFromDb();
		
		CTabFolder pluginsManagementTabs = new CTabFolder(parent, SWT.BORDER);
		pluginsManagementTabs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		CTabItem overviewTab = new CTabItem (pluginsManagementTabs, SWT.NONE);
		overviewTab.setText ("Overview");

		Composite overviewTabContents = new Composite(pluginsManagementTabs, SWT.NONE);
		overviewTabContents.setLayout(new GridLayout(2, false));

		PluginsOverview.createPluginsOverview(overviewTabContents, plugins);
		PluginsTable.createPluginsTable(overviewTabContents, pluginsManagementTabs, plugins);
		
		overviewTab.setControl (overviewTabContents);
		pluginsManagementTabs.setSelection(0);
	}
}

