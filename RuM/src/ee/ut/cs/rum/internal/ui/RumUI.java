package ee.ut.cs.rum.internal.ui;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.administration.ui.SystemAdministrationUI;
import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.internal.Activator;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

public class RumUI extends AbstractEntryPoint {
	private static final long serialVersionUID = 1282027955721012064L;
	
	private Composite rumUiParentComposite;
	private ServerPushSession pushSession;
	
	private static RumController rumController = Activator.getRumController();
	
	public void createContents(Composite parent) {
		this.rumUiParentComposite=parent;
		initializeSession();
		
		NavigationMenu navigationMenu = new NavigationMenu(parent);

		Composite sectionContainer = new Composite(parent, SWT.NONE);
		sectionContainer.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		sectionContainer.setLayout(new StackLayout());
		
		Composite workspaceSection = new WorkspaceUI(sectionContainer, rumController);
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
	
	//Session setup
	private void initializeSession() {
		//Needed for server side UI updates
		pushSession = new ServerPushSession();
		pushSession.start();
		
		//Cleanup when the session ends
		rumUiParentComposite.addDisposeListener(new DisposeListener() {
			private static final long serialVersionUID = -1858917710956550706L;

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				pushSession.stop();
				Activator.getLogger().info("ee.ut.cs.rum.RumUI parent disposed");
			}
		});
	}

}
