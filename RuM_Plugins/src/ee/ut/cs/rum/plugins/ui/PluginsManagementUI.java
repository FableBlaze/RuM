package ee.ut.cs.rum.plugins.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.plugins.internal.ui.PluginsOverview;
import ee.ut.cs.rum.plugins.internal.ui.PluginsTable;

public final class PluginsManagementUI {
	private PluginsManagementUI() {
	}

	public static void createPluginsManagementUI(Composite parent) {
		CTabFolder pluginsManagementTabs = new CTabFolder(parent, SWT.BORDER);
		pluginsManagementTabs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		CTabItem overviewTab = new CTabItem (pluginsManagementTabs, SWT.NONE);
		overviewTab.setText ("Overview");

		Composite overviewTabContents = new Composite(pluginsManagementTabs, SWT.NONE);
		overviewTabContents.setLayout(new GridLayout(2, false));

		PluginsOverview.createPluginsOverview(overviewTabContents);
		PluginsTable.createPluginsTable(overviewTabContents, pluginsManagementTabs);
		
		overviewTab.setControl (overviewTabContents);
		pluginsManagementTabs.setSelection(0);
	}
}

