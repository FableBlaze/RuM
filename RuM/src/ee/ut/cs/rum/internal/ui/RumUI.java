package ee.ut.cs.rum.internal.ui;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.administration.ui.SystemAdministrationUI;
import ee.ut.cs.rum.internal.Activator;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

public class RumUI extends AbstractEntryPoint {
	private static final long serialVersionUID = 1282027955721012064L;

	public void createContents(Composite parent) {
		NavigationMenu navigationMenu = new NavigationMenu(parent);

		Composite sectionContainer = new Composite(parent, SWT.NONE);
		sectionContainer.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		sectionContainer.setLayout(new StackLayout());
		
		Composite workspaceSection = new WorkspaceUI(sectionContainer);
		Composite pluginsManagementSection = new PluginsManagementUI(sectionContainer);
		Composite systemAdministrationSection = new SystemAdministrationUI(sectionContainer);
		
		((StackLayout)sectionContainer.getLayout()).topControl = workspaceSection;
		sectionContainer.layout();

		new NavigationButton(navigationMenu, "Workspace", workspaceSection, sectionContainer);
		new NavigationButton(navigationMenu, "Plugins management", pluginsManagementSection, sectionContainer);
		new NavigationButton(navigationMenu, "System administration", systemAdministrationSection, sectionContainer);
		
		navigationMenu.layout();
		
		Activator.getLogger().info("Someone opened ee.ut.cs.rum.RumUI");
	}

}
