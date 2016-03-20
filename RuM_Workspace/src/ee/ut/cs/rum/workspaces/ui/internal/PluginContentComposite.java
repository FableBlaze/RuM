package ee.ut.cs.rum.workspaces.ui.internal;

import org.eclipse.swt.widgets.Composite;

public class PluginContentComposite extends Composite {
	private static final long serialVersionUID = 496370581353877051L;

	public PluginContentComposite(Composite parent, int style) {
		super(parent, style);
	}
	
	@Override
	public Composite getParent() {
		// TODO: Restrict plugin access to parent control
		return super.getParent();
	}

}
