package ee.ut.cs.rum.plugins.internal.ui.details;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.util.PluginAccess;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class PluginDetails extends ScrolledComposite {
	private static final long serialVersionUID = 458942786595146853L;
	
	private Long pluginId;
	private Composite content;
	
	public PluginDetails(PluginsManagementUI pluginsManagementUI, Long pluginId) {
		super(pluginsManagementUI, SWT.CLOSE | SWT.H_SCROLL | SWT.V_SCROLL);
		
		this.pluginId = pluginId;
		
		this.content = new Composite(this, SWT.NONE);
		content.setLayout(new GridLayout(2, false));
		this.setContent(content);
		
		Plugin plugin = PluginAccess.getPluginDataFromDb(pluginId);
		
		createContents(plugin);
		
		content.setSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	private void createContents(Plugin plugin) {
		Label label = new Label (content, SWT.NONE);
		label.setText("Plugin details");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) label.getLayoutData()).horizontalSpan = ((GridLayout) content.getLayout()).numColumns;
		
		label = new Label (content, SWT.NONE);
		label.setText("Id:");
		label = new Label (content, SWT.NONE);
		label.setText(plugin.getId().toString());

		label = new Label (content, SWT.NONE);
		label.setText("Symbolic name:");
		label = new Label (content, SWT.NONE);
		label.setText(plugin.getSymbolicName());

		label = new Label (content, SWT.NONE);
		label.setText("Version:");
		label = new Label (content, SWT.NONE);
		label.setText(plugin.getVersion());

		label = new Label (content, SWT.NONE);
		label.setText("Name:");
		label = new Label (content, SWT.NONE);
		label.setText(plugin.getName());
		
		label = new Label (content, SWT.NONE);
		label.setText("Vendor:");
		label = new Label (content, SWT.NONE);
		label.setText(plugin.getVendor());
		
		label = new Label (content, SWT.NONE);
		label.setText("Description:");
		label = new Label (content, SWT.NONE);
		label.setText(plugin.getDescription());

		label = new Label (content, SWT.NONE);
		label.setText("Activator:");
		label = new Label (content, SWT.NONE);
		label.setText(plugin.getActivator());

		label = new Label (content, SWT.NONE);
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		label.setText("Imported packages:");
		
		//TODO: Should parse import packages list better
		Composite importedPackagesContainer = new Composite(content, SWT.NONE);
		importedPackagesContainer.setLayout(new FillLayout(SWT.VERTICAL));
		for (String importedPackage : plugin.getImportPackage().split("\",")) {
			label = new Label (importedPackagesContainer, SWT.NONE);
			label.setText(importedPackage);
		}
		
		label = new Label (content, SWT.NONE);
		label.setText("Original filename:");
		label = new Label (content, SWT.NONE);
		label.setText(plugin.getOriginalFilename());
		
		label = new Label (content, SWT.NONE);
		label.setText("Uploaded at:");
		label = new Label (content, SWT.NONE);
		label.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(plugin.getUploadedAt()));
		
		label = new Label (content, SWT.NONE);
		label.setText("Uploaded by:");
		label = new Label (content, SWT.NONE);
		label.setText(plugin.getUploadedBy());

		label = new Label (content, SWT.NONE);
		label.setText("File path:");
		label = new Label (content, SWT.NONE);
		label.setText(plugin.getFileLocation());
	}
	
	public Long getPluginId() {
		return pluginId;
	}

}
