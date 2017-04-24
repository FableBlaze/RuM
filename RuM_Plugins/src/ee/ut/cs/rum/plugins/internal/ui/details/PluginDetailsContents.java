package ee.ut.cs.rum.plugins.internal.ui.details;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.plugins.configuration.util.PluginUtils;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;

public class PluginDetailsContents extends ExpandBar implements RumUpdatableView {
	private static final long serialVersionUID = -4774110099028052424L;

	private Display display;
	private RumController rumController;
	private Plugin plugin;
	
	private Label enabledLabel;

	public PluginDetailsContents(PluginDetails pluginDetails, Plugin plugin, RumController rumController) {
		super(pluginDetails, SWT.V_SCROLL);

		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.PLUGIN);
		
		this.plugin = plugin;

		ExpandItem detailsExpandItem = new ExpandItem (this, SWT.NONE, 0);
		detailsExpandItem.setText("Plugin details");
		Composite detailsComposite = createDetailsComposite();
		detailsExpandItem.setHeight(detailsComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		detailsExpandItem.setControl(detailsComposite);

		ExpandItem configurationUiExpandItem = new ExpandItem (this, SWT.NONE, 1);
		configurationUiExpandItem.setText("Plugin configuration UI");
		Composite configurationUiComposite = createConfigurationUiComposite();
		configurationUiExpandItem.setHeight(configurationUiComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		configurationUiExpandItem.setControl(configurationUiComposite);

		ExpandItem importedPackagesExpandItem = new ExpandItem (this, SWT.NONE, 2);
		importedPackagesExpandItem.setText("Imported packages");
		Composite importedPackagesComposite = createImportedPackagesComposite();
		importedPackagesExpandItem.setHeight(importedPackagesComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		importedPackagesExpandItem.setControl(importedPackagesComposite);

		detailsExpandItem.setExpanded(true);
	}

	private ScrolledComposite createDetailsComposite() {
		ScrolledComposite detailsComposite = new ScrolledComposite(this, SWT.H_SCROLL);

		Composite detailsContent = new Composite (detailsComposite, SWT.NONE);
		GridLayout layout = new GridLayout (2, false);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		detailsContent.setLayout(layout);

		Label label = new Label (detailsContent, SWT.NONE);
		label.setText("Id:");
		label = new Label (detailsContent, SWT.NONE);
		label.setText(plugin.getId().toString());

		label = new Label (detailsContent, SWT.NONE);
		label.setText("Symbolic name:");
		label = new Label (detailsContent, SWT.NONE);
		label.setText(plugin.getBundleSymbolicName());

		label = new Label (detailsContent, SWT.NONE);
		label.setText("Version:");
		label = new Label (detailsContent, SWT.NONE);
		label.setText(plugin.getBundleVersion());

		label = new Label (detailsContent, SWT.NONE);
		label.setText("Name:");
		label = new Label (detailsContent, SWT.NONE);
		label.setText(plugin.getBundleName());

		label = new Label (detailsContent, SWT.NONE);
		label.setText("Vendor:");
		label = new Label (detailsContent, SWT.NONE);
		label.setText(plugin.getBundleVendor());

		label = new Label (detailsContent, SWT.NONE);
		label.setText("Description:");
		label = new Label (detailsContent, SWT.NONE);
		label.setText(plugin.getBundleDescription());

		label = new Label (detailsContent, SWT.NONE);
		label.setText("Activator:");
		label = new Label (detailsContent, SWT.NONE);
		label.setText(plugin.getBundleActivator());

		label = new Label (detailsContent, SWT.NONE);
		label.setText("Original filename:");
		label = new Label (detailsContent, SWT.NONE);
		label.setText(plugin.getOriginalFilename());

		label = new Label (detailsContent, SWT.NONE);
		label.setText("Uploaded at:");
		label = new Label (detailsContent, SWT.NONE);
		label.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(plugin.getCreatedAt()));

		label = new Label (detailsContent, SWT.NONE);
		label.setText("Uploaded by:");
		label = new Label (detailsContent, SWT.NONE);
		label.setText(plugin.getCreatedBy());

		label = new Label (detailsContent, SWT.NONE);
		label.setText("File path:");
		label = new Label (detailsContent, SWT.NONE);
		label.setText(plugin.getFileLocation());

		label = new Label (detailsContent, SWT.NONE);
		label.setText("Enabled:");
		enabledLabel = new Label (detailsContent, SWT.NONE);
		enabledLabel.setText(plugin.getEnabled().toString());

		detailsComposite.setContent(detailsContent);
		detailsContent.setSize(detailsContent.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		return detailsComposite;
	}

	private ScrolledComposite createConfigurationUiComposite() {
		ScrolledComposite configurationUiComposite = new ScrolledComposite(this, SWT.H_SCROLL);

		Composite configurationUiContent = new Composite (configurationUiComposite, SWT.NONE);
		GridLayout layout = new GridLayout (2, false);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		configurationUiContent.setLayout(layout);

		PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(plugin);
		new PluginConfigurationComposite(configurationUiContent, pluginInfo, rumController, null, null);

		configurationUiComposite.setContent(configurationUiContent);
		configurationUiContent.setSize(configurationUiContent.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		return configurationUiComposite;
	}

	private Composite createImportedPackagesComposite() {
		ScrolledComposite importedPackagesComposite = new ScrolledComposite(this, SWT.H_SCROLL);

		//TODO: Should parse import packages list better
		Composite importedPackagesContent = new Composite(importedPackagesComposite, SWT.NONE);
		FillLayout layout = new FillLayout(SWT.VERTICAL);
		layout.marginHeight = layout.marginWidth = 10;
		importedPackagesContent.setLayout(layout);

		for (String importedPackage : plugin.getBundleImportPackage().split("\",")) {
			Label label = new Label (importedPackagesContent, SWT.NONE);
			label.setText(importedPackage);
		}

		importedPackagesComposite.setContent(importedPackagesContent);
		importedPackagesContent.setSize(importedPackagesContent.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		return importedPackagesComposite;
	}
	
	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof Plugin && ((Plugin) updatedEntity).getId()==plugin.getId() && updateType == ControllerUpdateType.MODIFIY) {
			display.asyncExec(new Runnable() {
				public void run() {
					enabledLabel.setText(((Plugin) updatedEntity).getEnabled().toString());
					enabledLabel.pack();
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
