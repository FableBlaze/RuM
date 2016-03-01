package ee.ut.cs.rum.ui;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.Activator;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class RumUI extends AbstractEntryPoint {
	private static final long serialVersionUID = 1282027955721012064L;

	public void createContents(Composite parent) {
		NavigationMenu navigationMenu = new NavigationMenu(parent);

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		StackLayout stackLayout = new StackLayout();
		container.setLayout(stackLayout);

		Composite workspaceSection = new Composite(container, SWT.BORDER);
		workspaceSection.setLayout(new GridLayout(2, false));
		Label l = new Label(workspaceSection, SWT.NONE);
		l.setText("Workspace contents (TODO)");
		
		Composite pluginsManagementSection = new PluginsManagementUI(container);
		
		stackLayout.topControl = workspaceSection;
		container.layout();

		new NavigationButton(navigationMenu, "Workspace", workspaceSection, stackLayout, container);
		new NavigationButton(navigationMenu, "Plugins management", pluginsManagementSection, stackLayout, container);
		
		navigationMenu.layout();
		
		Activator.getLogger().info("Someone opened ee.ut.cs.rum.RumUI");
	}

}
