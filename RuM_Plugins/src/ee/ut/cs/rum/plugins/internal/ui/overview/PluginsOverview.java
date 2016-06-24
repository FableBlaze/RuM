package ee.ut.cs.rum.plugins.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.util.PluginAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.plugins.internal.ui.dialog.PluginUploadDialog;

public class PluginsOverview extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = 6363000997779117721L;

	private Display display;
	private RumController rumController;
	
	private Label totalPlugins;

	public PluginsOverview(OverviewTabContents overviewTabContents, RumController rumController) {
		super(overviewTabContents, SWT.NONE);
		
		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.PLUGIN);
		
		this.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
		this.setLayout(new GridLayout(2, false));
		((GridData) this.getLayoutData()).widthHint=200;
		
		createContents();
	}
	

	private void createContents() {
		Label label = new Label(this, SWT.NONE);
		label.setText("Total plugins:");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));
		totalPlugins = new Label(this, SWT.NONE);
		totalPlugins.setText(Integer.toString(PluginAccess.getPluginsDataFromDb().size()));
		totalPlugins.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));
		
		Button addPluginDialogueButton = new Button(this, SWT.PUSH);
		addPluginDialogueButton.setText("Add plugin");
		addPluginDialogueButton.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, true, true));
		((GridData) addPluginDialogueButton.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;

		addPluginDialogueButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 5362888762173659283L;

			@Override
			public void handleEvent(Event arg0) {
				PluginUploadDialog pluginUploadDialog = new PluginUploadDialog(Display.getCurrent().getActiveShell(), rumController);
				pluginUploadDialog.open();
			}
		});
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof Plugin) {
			display.asyncExec(new Runnable() {
				public void run() {
					int totalPluginsCount = Integer.parseInt(totalPlugins.getText());
					switch (updateType) {
					case CREATE:
						totalPluginsCount+=1;
						break;
					case DELETE:
						totalPluginsCount-=1;
						break;
					default:
						break;
					}
					totalPlugins.setText(Integer.toString(totalPluginsCount));							
				}
			});
		}
	}

	@Override
	public void dispose() {
		rumController.unregisterView(this, ControllerEntityType.PLUGIN);
		super.dispose();
	}
}
