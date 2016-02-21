package ee.ut.cs.rum.plugins.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import ee.ut.cs.rum.plugins.internal.ui.PluginsTable;

public final class PluginsManagementUI {
	private PluginsManagementUI() {
	}

	public static void createPluginsManagementUI(Composite parent) {
		CTabFolder pluginsManagementTabs = new CTabFolder(parent, SWT.BORDER);
		pluginsManagementTabs.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));

		CTabItem overviewTab = new CTabItem (pluginsManagementTabs, SWT.NONE);
		overviewTab.setText ("Overview");
		Button button = new Button (pluginsManagementTabs, SWT.PUSH);
		button.setText ("Overview ");

		Composite c = new Composite(pluginsManagementTabs, SWT.NONE);
		c.setLayout(new GridLayout());

		PluginsTable.createPluginsTable(c, pluginsManagementTabs);
		overviewTab.setControl (c);
		pluginsManagementTabs.setSelection(0);
	}
}

