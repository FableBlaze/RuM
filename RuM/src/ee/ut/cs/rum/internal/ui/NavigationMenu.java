package ee.ut.cs.rum.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class NavigationMenu extends Composite {
	private static final long serialVersionUID = -7229992311867297026L;
	
	public NavigationMenu(Composite parent) {
		super(parent, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
	}
	
	@Override
	public void layout() {
		if (this.getChildren().length > 0) {
			this.setLayout(new GridLayout(this.getChildren().length, false));
		}
		super.layout();
	}
}
