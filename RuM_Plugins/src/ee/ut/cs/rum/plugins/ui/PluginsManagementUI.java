package ee.ut.cs.rum.plugins.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.ui.OverviewTabContents;
import ee.ut.cs.rum.plugins.internal.util.PluginsData;

public class PluginsManagementUI extends CTabFolder {
	private static final long serialVersionUID = 2978902689070409076L;
	
	private List<Plugin> plugins;
	private CTabItem overviewTab;
	private OverviewTabContents overviewTabContents;
	
	public PluginsManagementUI(Composite parent) {
		super(parent, SWT.BORDER);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.plugins = PluginsData.getPluginsDataFromDb();
		
		this.overviewTab = new CTabItem (this, SWT.NONE);
		overviewTab.setText ("Overview");
		
		this.overviewTabContents = new OverviewTabContents(this);
		overviewTab.setControl (overviewTabContents);
		
		this.setSelection(0);
	}
	
	
	public List<Plugin> getPlugins() {
		return plugins;
	}
}

