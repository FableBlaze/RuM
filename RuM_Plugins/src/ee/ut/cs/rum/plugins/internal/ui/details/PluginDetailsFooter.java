package ee.ut.cs.rum.plugins.internal.ui.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.download.PluginDownloadButton;

public class PluginDetailsFooter extends Composite {
	private static final long serialVersionUID = -8253905754328727271L;

	public PluginDetailsFooter(PluginDetails pluginDetails, Plugin plugin, RumController rumController) {
		super(pluginDetails, SWT.NONE);
		
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = false;
		this.setLayout(rowLayout);
		
		new PluginDownloadButton(this, plugin);
		
		new PluginEnableButton(this, plugin, rumController);
	}

}
