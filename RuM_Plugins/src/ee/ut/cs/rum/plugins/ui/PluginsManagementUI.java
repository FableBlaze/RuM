package ee.ut.cs.rum.plugins.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.plugins.internal.ui.OverviewTabContents;

public class PluginsManagementUI extends CTabFolder {
	private static final long serialVersionUID = 2978902689070409076L;
	
	private CTabItem overviewTab;
	private OverviewTabContents overviewTabContents;
	
	public PluginsManagementUI(Composite parent) {
		super(parent, SWT.BORDER);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.overviewTab = new CTabItem (this, SWT.NONE);
		overviewTab.setText ("Overview");
		
		this.overviewTabContents = new OverviewTabContents(this);
		overviewTab.setControl (overviewTabContents);
		
		this.setSelection(0);
	}
}

