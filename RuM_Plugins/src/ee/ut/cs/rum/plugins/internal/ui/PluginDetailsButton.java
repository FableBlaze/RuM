package ee.ut.cs.rum.plugins.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class PluginDetailsButton extends Button {
	private static final long serialVersionUID = -7636382139932347297L;
	
	private Long pluginId;
	
	public PluginDetailsButton(Composite parent, Long pluginId) {
		super(parent, SWT.NONE);
		this.pluginId = pluginId;
	}
	
	public Long getPluginId() {
		return pluginId;
	}

}
