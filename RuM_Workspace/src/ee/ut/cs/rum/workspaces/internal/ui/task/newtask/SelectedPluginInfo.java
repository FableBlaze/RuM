package ee.ut.cs.rum.workspaces.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import ee.ut.cs.rum.database.domain.Plugin;

public class SelectedPluginInfo extends ScrolledComposite {
	private static final long serialVersionUID = 2323888161640392669L;

	private Label headerLabel;
	private boolean contentsInitialized = false;

	private Composite content;

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

	public SelectedPluginInfo(Composite parent) {
		super(parent, SWT.H_SCROLL | SWT.V_SCROLL);

		this.content = new Composite(this, SWT.NONE);
		content.setLayout(new GridLayout(2, false));
		this.setContent(content);
		this.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));

		headerLabel = new Label(content, SWT.NONE);
		headerLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) headerLabel.getLayoutData()).horizontalSpan = ((GridLayout) content.getLayout()).numColumns;
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
	}

	private void createContents() {
		symbolicNameLabel = new Label (content, SWT.NONE);
		symbolicNameLabel.setText("Symbolic name:");
		symbolicNameValueLabel = new Label (content, SWT.NONE);

		versionLabel = new Label (content, SWT.NONE);
		versionLabel.setText("Version:");
		versionValueLabel = new Label (content, SWT.NONE);

		nameLabel = new Label (content, SWT.NONE);
		nameLabel.setText("Name:");
		nameValueLabel = new Label (content, SWT.NONE);

		vendorLabel = new Label (content, SWT.NONE);
		vendorLabel.setText("Vendor:");
		vendorValueLabel = new Label (content, SWT.NONE);

		descriptionLabel = new Label (content, SWT.NONE);
		descriptionLabel.setText("Description:");
		descriptionValueLabel = new Label (content, SWT.NONE);
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
