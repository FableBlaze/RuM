package ee.ut.cs.rum.ui;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import ee.ut.cs.rum.Activator;
import ee.ut.cs.rum.administration.ui.SystemAdministrationUI;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

public class RumUI extends AbstractEntryPoint {
	private static final long serialVersionUID = 1282027955721012064L;

	public void createContents(Composite parent) {
		NavigationMenu navigationMenu = new NavigationMenu(parent);

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		StackLayout stackLayout = new StackLayout();
		container.setLayout(stackLayout);
		
		Composite workspaceSection = new WorkspaceUI(container);
		Composite pluginsManagementSection = new PluginsManagementUI(container);
		Composite systemAdministrationSection = new SystemAdministrationUI(container);
		
		stackLayout.topControl = workspaceSection;
		container.layout();

		new NavigationButton(navigationMenu, "Workspaces", workspaceSection, stackLayout, container);
		new NavigationButton(navigationMenu, "Plugins management", pluginsManagementSection, stackLayout, container);
		new NavigationButton(navigationMenu, "System administration", systemAdministrationSection, stackLayout, container);
		
		navigationMenu.layout();
		
		Activator.getLogger().info("Someone opened ee.ut.cs.rum.RumUI");
	}

}
