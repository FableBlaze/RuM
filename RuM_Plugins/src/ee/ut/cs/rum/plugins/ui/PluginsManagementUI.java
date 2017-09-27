package ee.ut.cs.rum.plugins.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.plugins.internal.ui.overview.OverviewTabContents;

public class PluginsManagementUI extends CTabFolder {
	private static final long serialVersionUID = 2978902689070409076L;
	
	public static final String SECTION_NAME = "pluginsManagementUI";
	
	private CTabItem overviewTab;
	private OverviewTabContents overviewTabContents;
	
	public PluginsManagementUI(Composite parent, RumController rumController) {
		super(parent, SWT.NONE);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.overviewTab = new CTabItem (this, SWT.NONE);
		overviewTab.setText ("Overview");
		
		this.overviewTabContents = new OverviewTabContents(this, rumController);
		overviewTab.setControl (overviewTabContents);
		
		this.setSelection(0);
	}
}

