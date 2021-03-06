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
import ee.ut.cs.rum.files.ui.FilesManagementUI;
import ee.ut.cs.rum.internal.Activator;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

public class RumUI extends AbstractEntryPoint {
	private static final long serialVersionUID = 1282027955721012064L;
	
	private Composite rumUiParentComposite;
	private ServerPushSession pushSession;
	
	private Composite sectionContainer;
	private Composite workspaceSection;
	private Composite filesSection;
	private Composite pluginsManagementSection;
	private Composite systemAdministrationSection;
	
	private static final RumController rumController = Activator.getRumController();
	
	public void createContents(Composite parent) {
		this.rumUiParentComposite=parent;
		initializeSession();
		
		new RumHeader(rumUiParentComposite, rumController, this);

		sectionContainer = new Composite(rumUiParentComposite, SWT.NONE);
		sectionContainer.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		sectionContainer.setLayout(new StackLayout());
		
		this.setVisibleSection(WorkspaceUI.SECTION_NAME);
		
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
			}
		});
	}
	
	public void setVisibleSection(String sectionName) {
		switch (sectionName) {
		case WorkspaceUI.SECTION_NAME:
			if (workspaceSection==null) {
				workspaceSection = new WorkspaceUI(sectionContainer, rumController);
			}
			((StackLayout)sectionContainer.getLayout()).topControl = workspaceSection;
			sectionContainer.layout();
			break;
		case FilesManagementUI.SECTION_NAME:
			if (filesSection==null) {
				filesSection = new FilesManagementUI(sectionContainer, rumController);
			}
			((StackLayout)sectionContainer.getLayout()).topControl = filesSection;
			sectionContainer.layout();
			break;
		case PluginsManagementUI.SECTION_NAME:
			if (pluginsManagementSection==null) {
				pluginsManagementSection = new PluginsManagementUI(sectionContainer, rumController);
			}
			((StackLayout)sectionContainer.getLayout()).topControl = pluginsManagementSection;
			sectionContainer.layout();
			break;
		case SystemAdministrationUI.SECTION_NAME:
			if (systemAdministrationSection==null) {
				systemAdministrationSection = new SystemAdministrationUI(sectionContainer, rumController);
			}
			((StackLayout)sectionContainer.getLayout()).topControl = systemAdministrationSection;
			sectionContainer.layout();
			break;
		default:
			break;
		}
	}
}
