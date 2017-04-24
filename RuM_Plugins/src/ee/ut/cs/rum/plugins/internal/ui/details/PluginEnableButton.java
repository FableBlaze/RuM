package ee.ut.cs.rum.plugins.internal.ui.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.plugins.internal.Activator;

public class PluginEnableButton extends Button implements RumUpdatableView {
	private static final long serialVersionUID = -1372528497637726846L;

	private Display display;
	private RumController rumController;
	private Plugin plugin;

	public PluginEnableButton(PluginDetailsFooter pluginDetailsFooter, Plugin plugin, RumController rumController) {
		super(pluginDetailsFooter, SWT.PUSH);

		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.PLUGIN);

		this.plugin = plugin;

		if (plugin.getEnabled()==true) {
			this.setText("Disable");			
		} else {
			this.setText("Enable");
		}

		this.addSelectionListener(this.createSelectionListener());
	}

	private SelectionListener createSelectionListener() {
		return new SelectionListener() {
			private static final long serialVersionUID = -5763963548124956217L;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Activator.getLogger().info("Plugin enable/disable: " + plugin.getId().toString());
				Plugin tmpPlugin = plugin;
				PluginEnableButton.this.setEnabled(false);
				if (tmpPlugin.getEnabled()==true) {
					tmpPlugin.setEnabled(false);
				} else {
					tmpPlugin.setEnabled(true);
				}
				rumController.changeData(ControllerUpdateType.MODIFIY, ControllerEntityType.PLUGIN, tmpPlugin, "TODO");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		};
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof Plugin && ((Plugin) updatedEntity).getId()==plugin.getId() && updateType == ControllerUpdateType.MODIFIY) {
			display.asyncExec(new Runnable() {
				public void run() {
					if (((Plugin) updatedEntity).getEnabled()==true) {
						PluginEnableButton.this.setText("Disable");
					} else {
						PluginEnableButton.this.setText("Enable");
					}
					PluginEnableButton.this.setEnabled(true);
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
