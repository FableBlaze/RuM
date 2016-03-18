package ee.ut.cs.rum.plugins.internal.ui.overview.pluginstable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.plugins.internal.ui.overview.OverviewTabContents;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class PluginsTableComposite extends Composite {
	private static final long serialVersionUID = 6151369187697732888L;
	
	private PluginsTableViewer pluginsTableViewer;
	
	public PluginsTableComposite(OverviewTabContents overviewTabContents, PluginsManagementUI pluginsManagementUI) {
		super(overviewTabContents, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(1, false));
		
		Text searchPlaceholder = new Text(this, SWT.BORDER);
		searchPlaceholder.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		searchPlaceholder.setText("Enter filter string here... (TODO)");
		
		this.pluginsTableViewer = new PluginsTableViewer(this, pluginsManagementUI);
	}

	public PluginsTableViewer getPluginsTableViewer() {
		return pluginsTableViewer;
	}

}
