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
	private Label enabledPlugins;
	private Label disabledPlugins;

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
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		totalPlugins = new Label(this, SWT.NONE);
		totalPlugins.setText(Integer.toString(PluginAccess.getAllPluginsDataFromDb().size()));
		totalPlugins.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Enabled plugins:");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		enabledPlugins = new Label(this, SWT.NONE);
		enabledPlugins.setText(Integer.toString(PluginAccess.getPluginsDataFromDb(true).size()));
		enabledPlugins.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		
		label = new Label(this, SWT.NONE);
		label.setText("Disabled plugins:");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));
		disabledPlugins = new Label(this, SWT.NONE);
		disabledPlugins.setText(Integer.toString(PluginAccess.getPluginsDataFromDb(false).size()));
		disabledPlugins.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));
		
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
					Plugin updatedPlugin = (Plugin)updatedEntity;
					int totalPluginsCount = Integer.parseInt(totalPlugins.getText());
					int enabledPluginsCount = Integer.parseInt(enabledPlugins.getText());
					int disabledPluginsCount = Integer.parseInt(disabledPlugins.getText());
					switch (updateType) {
					case CREATE:
						totalPluginsCount+=1;
						enabledPluginsCount+=1;
						break;
					case DELETE:
						totalPluginsCount-=1;
						if (updatedPlugin.getEnabled()==true) {
							enabledPluginsCount-=1;
						} else {
							disabledPluginsCount-=1;
						}
						break;
					case MODIFIY:
						if (updatedPlugin.getEnabled()==true) {
							enabledPluginsCount+=1;
							disabledPluginsCount-=1;
						} else {
							enabledPluginsCount-=1;
							disabledPluginsCount+=1;
						}
						break;
					default:
						break;
					}
					totalPlugins.setText(Integer.toString(totalPluginsCount));
					enabledPlugins.setText(Integer.toString(enabledPluginsCount));
					disabledPlugins.setText(Integer.toString(disabledPluginsCount));
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
