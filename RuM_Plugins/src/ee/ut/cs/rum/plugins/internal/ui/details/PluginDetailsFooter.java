package ee.ut.cs.rum.plugins.internal.ui.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.plugins.internal.download.PluginDownloadButton;

public class PluginDetailsFooter extends Composite {
	private static final long serialVersionUID = -8253905754328727271L;

	public PluginDetailsFooter(PluginDetails pluginDetails) {
		super(pluginDetails, SWT.NONE);
		
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = false;
		this.setLayout(rowLayout);
		
		new PluginDownloadButton(this, pluginDetails.getPlugin());
		
		Button b = new Button(this, SWT.PUSH);
		b.setText("Enable/Disable (TODO)");
	}

}
