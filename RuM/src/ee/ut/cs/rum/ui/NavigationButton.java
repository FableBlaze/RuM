package ee.ut.cs.rum.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class NavigationButton extends Button {
	private static final long serialVersionUID = -2540683688422371891L;

	public NavigationButton(Composite parent, String buttonText, Control topControl, StackLayout stackLayout, Composite container) {
		super(parent, SWT.PUSH);
		
		this.setText(buttonText);
		this.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -7714989063610717365L;

			public void widgetSelected( SelectionEvent event ) {
				stackLayout.topControl = topControl;
				container.layout();
			}
		});
	}

}
