package ee.ut.cs.rum.plugins.configuration.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;

public class ScrolledPluginConfigurationComposite extends ScrolledComposite {
	private static final long serialVersionUID = 1011296086711861676L;

	public ScrolledPluginConfigurationComposite(Composite parent) {
		super(parent, SWT.H_SCROLL | SWT.V_SCROLL);
	}
	
	
}
