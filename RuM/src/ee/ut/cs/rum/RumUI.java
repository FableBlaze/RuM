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
	
	private Composite visibleComponent;
    private Shell hiddenComponentsShell;

	public void createContents( Composite parent ) {
		hiddenComponentsShell = new Shell();
		
		Composite workspacesUI = new Composite(hiddenComponentsShell, SWT.BORDER);
		Composite pluginsManagementUI = new PluginsManagementUI(parent);
		visibleComponent=pluginsManagementUI;
		
		
        Button workspacesButton = new Button(parent, SWT.PUSH);
        workspacesButton.setText("Workspaces");
        workspacesButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                if (visibleComponent!=workspacesUI) {
                	visibleComponent.setParent(hiddenComponentsShell);
                    workspacesUI.setParent(parent);
                    visibleComponent=workspacesUI;
                }
            }
        });
		
        Button pluginsManagemenButton = new Button(parent, SWT.PUSH);
        pluginsManagemenButton.setText("Plugins management");
        pluginsManagemenButton.addSelectionListener(new SelectionAdapter() {
        	public void widgetSelected(SelectionEvent event) {
        		if (visibleComponent!=pluginsManagementUI) {
        			visibleComponent.setParent(hiddenComponentsShell);
        			pluginsManagementUI.setParent(parent);
                    visibleComponent=pluginsManagementUI;
        		}
        	}
        });
		
		Activator.getLogger().info("Someone opened ee.ut.cs.rum.RumUI");
	}

}
