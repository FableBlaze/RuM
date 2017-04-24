package ee.ut.cs.rum.plugins.internal.download;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.UrlLauncher;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.Activator;
import ee.ut.cs.rum.plugins.internal.download.DownloadService;

public class PluginDownloadButton extends Button {
	private static final long serialVersionUID = 4261171125412165089L;
	
	private Plugin plugin;
	
	public PluginDownloadButton(Composite parent, Plugin plugin) {
		super(parent, SWT.NONE);
		
		this.plugin=plugin;
		
		this.setText("Download");
		
		this.addSelectionListener(this.createSelectionListener());
	}

	private SelectionListener createSelectionListener() {
		return new SelectionListener() {
			private static final long serialVersionUID = -622701191074305980L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Plugin plugin = ((PluginDownloadButton) arg0.getSource()).getPlugin();
				Activator.getLogger().info("Download plugin: " + plugin.getId().toString());
				
				DownloadService service = new DownloadService(plugin);
				service.register();
				UrlLauncher launcher = RWT.getClient().getService(UrlLauncher.class);
				launcher.openURL(service.getURL());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		};
	}

	protected Plugin getPlugin() {
		return this.plugin;
	}
}
