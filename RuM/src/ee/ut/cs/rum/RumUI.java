package ee.ut.cs.rum;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.widgets.Composite;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class RumUI extends AbstractEntryPoint {
	private static final long serialVersionUID = 1282027955721012064L;

	public void createContents( Composite parent ) {
		new PluginsManagementUI(parent);
		Activator.getLogger().info("Someone opened ee.ut.cs.rum.RumUI");
	}

}
