package ee.ut.cs.rum.plugins.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.plugins.internal.ui.overview.pluginstable.PluginsTableComposite;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class OverviewTabContents extends Composite {
	private static final long serialVersionUID = 5073868530789359506L;
	
	public OverviewTabContents(PluginsManagementUI pluginsManagementUI, RumController rumController) {
		super(pluginsManagementUI, SWT.NONE);
		this.setLayout(new GridLayout(2, false));
		
		new PluginsOverview(this, rumController);
		new PluginsTableComposite(this, pluginsManagementUI, rumController);
	}
}
