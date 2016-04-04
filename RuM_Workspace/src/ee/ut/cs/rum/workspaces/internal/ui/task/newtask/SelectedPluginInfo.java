package ee.ut.cs.rum.workspaces.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.Plugin;

public class SelectedPluginInfo extends Composite {
	private static final long serialVersionUID = 2323888161640392669L;

	private Label headerLabel;
	private boolean contentsInitialized = false;

	private Label symbolicNameLabel;
	private Label symbolicNameValueLabel;
	private Label versionLabel;
	private Label versionValueLabel;
	private Label nameLabel;
	private Label nameValueLabel;
	private Label vendorLabel;
	private Label vendorValueLabel;
	private Label descriptionLabel;
	private Label descriptionValueLabel;

	public SelectedPluginInfo(Composite content) {
		super(content, SWT.NONE);

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		((GridData) this.getLayoutData()).widthHint=400;
		this.setLayout(new GridLayout(2, false));

		headerLabel = new Label(this, SWT.NONE);
		headerLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) headerLabel.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		headerLabel.setText("No plugin selected");
	}

	public void updateSelectedPluginInfo(Plugin plugin) {
		if (!contentsInitialized) {
			createContents();
			headerLabel.setText("Plugin details");
			contentsInitialized=true;
			updateContents(plugin);
		} else if (contentsInitialized && plugin!=null) {
			updateContents(plugin);
		} else if (contentsInitialized && plugin==null) {
			removeContents();
			contentsInitialized=false;
			headerLabel.setText("No plugin selected");
		}
		layout();
	}

	private void createContents() {
		symbolicNameLabel = new Label (this, SWT.NONE);
		symbolicNameLabel.setText("Symbolic name:");
		symbolicNameValueLabel = new Label (this, SWT.NONE);

		versionLabel = new Label (this, SWT.NONE);
		versionLabel.setText("Version:");
		versionValueLabel = new Label (this, SWT.NONE);

		nameLabel = new Label (this, SWT.NONE);
		nameLabel.setText("Name:");
		nameValueLabel = new Label (this, SWT.NONE);

		vendorLabel = new Label (this, SWT.NONE);
		vendorLabel.setText("Vendor:");
		vendorValueLabel = new Label (this, SWT.NONE);

		descriptionLabel = new Label (this, SWT.NONE);
		descriptionLabel.setText("Description:");
		descriptionValueLabel = new Label (this, SWT.NONE);
	}

	private void removeContents() {
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
		if (!nameLabel.isDisposed()) {
			nameLabel.dispose();
		}
		if (!nameValueLabel.isDisposed()) {
			nameValueLabel.dispose();
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
		this.symbolicNameValueLabel.setText(plugin.getBundleSymbolicName());
		this.versionValueLabel.setText(plugin.getBundleVersion());
		this.nameValueLabel.setText(plugin.getBundleName());
		this.vendorValueLabel.setText(plugin.getBundleVendor());
		this.descriptionValueLabel.setText(plugin.getBundleDescription());
	}

}
