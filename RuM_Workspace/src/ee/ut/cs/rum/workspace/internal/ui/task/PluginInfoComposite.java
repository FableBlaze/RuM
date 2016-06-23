package ee.ut.cs.rum.workspace.internal.ui.task;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import ee.ut.cs.rum.database.domain.Plugin;

public class PluginInfoComposite extends Composite {
	private static final long serialVersionUID = 2323888161640392669L;

	private Label headerLabel;
	private boolean contentsInitialized = false;

	private Label nameLabel;
	private Label nameValueLabel;
	private Label symbolicNameLabel;
	private Label symbolicNameValueLabel;
	private Label versionLabel;
	private Label versionValueLabel;
	private Label vendorLabel;
	private Label vendorValueLabel;
	private Label descriptionLabel;
	private Label descriptionValueLabel;

	public PluginInfoComposite(Composite parent) {
		super(parent, SWT.NONE);

		this.setLayout(new GridLayout(2, false));

		headerLabel = new Label(this, SWT.NONE);
		headerLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) headerLabel.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		headerLabel.setText("No plugin selected");
	}

	public void updateSelectedPluginInfo(Plugin plugin) {
		if (!contentsInitialized) {
			createContents();
			headerLabel.setText("Plugin info");
			contentsInitialized=true;
			updateContents(plugin);
		} else if (contentsInitialized && plugin!=null) {
			updateContents(plugin);
		} else if (contentsInitialized && plugin==null) {
			removeContents();
			contentsInitialized=false;
			headerLabel.setText("No plugin selected");
		}
		this.layout();
	}

	private void createContents() {		
		nameLabel = new Label (this, SWT.NONE);
		nameLabel.setText("Name:");
		nameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		nameValueLabel = new Label (this, SWT.NONE);
		
		symbolicNameLabel = new Label (this, SWT.NONE);
		symbolicNameLabel.setText("Symbolic name:");
		symbolicNameLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		symbolicNameValueLabel = new Label (this, SWT.NONE);

		versionLabel = new Label (this, SWT.NONE);
		versionLabel.setText("Version:");
		versionLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		versionValueLabel = new Label (this, SWT.NONE);

		vendorLabel = new Label (this, SWT.NONE);
		vendorLabel.setText("Vendor:");
		vendorLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		vendorValueLabel = new Label (this, SWT.NONE);

		descriptionLabel = new Label (this, SWT.NONE);
		descriptionLabel.setText("Description:");
		descriptionLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		descriptionValueLabel = new Label (this, SWT.NONE);
	}

	private void removeContents() {
		if (!nameLabel.isDisposed()) {
			nameLabel.dispose();
		}
		if (!nameValueLabel.isDisposed()) {
			nameValueLabel.dispose();
		}
		if (!symbolicNameLabel.isDisposed()) {
			symbolicNameLabel.dispose();
		}
		if (!symbolicNameValueLabel.isDisposed()) {
			symbolicNameValueLabel.dispose();
		}
		if (!versionLabel.isDisposed()) {
			versionLabel.dispose();
		}
		if (!versionValueLabel.isDisposed()) {
			versionValueLabel.dispose();
		}
		if (!vendorLabel.isDisposed()) {
			vendorLabel.dispose();
		}
		if (!vendorValueLabel.isDisposed()) {
			vendorValueLabel.dispose();
		}
		if (!descriptionLabel.isDisposed()) {
			descriptionLabel.dispose();
		}
		if (!descriptionValueLabel.isDisposed()) {
			descriptionValueLabel.dispose();
		}
	}

	private void updateContents(Plugin plugin) {
		this.nameValueLabel.setText(plugin.getBundleName());
		this.symbolicNameValueLabel.setText(plugin.getBundleSymbolicName());
		this.versionValueLabel.setText(plugin.getBundleVersion());
		this.vendorValueLabel.setText(plugin.getBundleVendor());
		this.descriptionValueLabel.setText(plugin.getBundleDescription());
	}

}
