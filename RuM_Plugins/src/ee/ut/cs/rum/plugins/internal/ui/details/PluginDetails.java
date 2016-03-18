package ee.ut.cs.rum.plugins.internal.ui.details;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.util.PluginAccess;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class PluginDetails extends Composite {
	private static final long serialVersionUID = 458942786595146853L;
	
	private Long pluginId;
	
	public PluginDetails(PluginsManagementUI pluginsManagementUI, Long pluginId) {
		super(pluginsManagementUI, SWT.CLOSE);
		this.setLayout(new GridLayout(2, false));
		this.pluginId = pluginId;
		
		Plugin plugin = PluginAccess.getPluginDataFromDb(pluginId);
		
		createContents(plugin);
	}
	
	private void createContents(Plugin plugin) {
		Label label = new Label (this, SWT.NONE);
		label.setText("Plugin details");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) label.getLayoutData()).horizontalSpan = 2;
		
		label = new Label (this, SWT.NONE);
		label.setText("Id:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getId().toString());

		label = new Label (this, SWT.NONE);
		label.setText("Symbolic name:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getSymbolicName());

		label = new Label (this, SWT.NONE);
		label.setText("Version:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getVersion());

		label = new Label (this, SWT.NONE);
		label.setText("Name:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getName());
		
		label = new Label (this, SWT.NONE);
		label.setText("Vendor:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getVendor());
		
		label = new Label (this, SWT.NONE);
		label.setText("Description:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getDescription());

		label = new Label (this, SWT.NONE);
		label.setText("Activator:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getActivator());

		label = new Label (this, SWT.NONE);
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		label.setText("Imported packages:");
		
		//TODO: Should parse import packages list better
		Composite importedPackagesContainer = new Composite(this, SWT.NONE);
		importedPackagesContainer.setLayout(new FillLayout(SWT.VERTICAL));
		for (String importedPackage : plugin.getImportPackage().split("\",")) {
			label = new Label (importedPackagesContainer, SWT.NONE);
			label.setText(importedPackage);
		}
		
		label = new Label (this, SWT.NONE);
		label.setText("Original filename:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getOriginalFilename());
		
		label = new Label (this, SWT.NONE);
		label.setText("Uploaded at:");
		label = new Label (this, SWT.NONE);
		label.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(plugin.getUploadedAt()));
		
		label = new Label (this, SWT.NONE);
		label.setText("Uploaded by:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getUploadedBy());

		label = new Label (this, SWT.NONE);
		label.setText("File path:");
		label = new Label (this, SWT.NONE);
		label.setText(plugin.getFileLocation());
	}
	
	public Long getPluginId() {
		return pluginId;
	}

}
