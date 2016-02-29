package ee.ut.cs.rum;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class RumUI extends AbstractEntryPoint {
	private static final long serialVersionUID = 1282027955721012064L;
	
	private PluginsManagementUI pluginsManagementUI;

	public void createContents( Composite parent ) {
		//TODO: Hidden shell does not seem like the correct way to handle navigation
		Shell hiddenComponentsParent = new Shell();
		
		Button b = new Button(parent, SWT.PUSH);
		b.setText("Plugins management");
		b.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -7891195942424898731L;
			public void widgetSelected(SelectionEvent event) {
				if (pluginsManagementUI.getParent()==hiddenComponentsParent) {pluginsManagementUI.setParent(parent);}
				else {pluginsManagementUI.setParent(hiddenComponentsParent);}
			}
		});
		
		pluginsManagementUI = new PluginsManagementUI(parent);
		
		Activator.getLogger().info("Someone opened ee.ut.cs.rum.RumUI");
	}

}
