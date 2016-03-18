package ee.ut.cs.rum.workspace.ui.internal.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.Plugin;

public class SelectedPluginInfo extends Composite {
	private static final long serialVersionUID = 2323888161640392669L;

	private Label initialLabel;
	private boolean contentsInitialized = false;

	private Label symbolicNameLabel;
	private Label versionLabel;
	private Label nameLabel;
	private Label vendorLabel;
	private Label descriptionLabel;

	SelectedPluginInfo(NewTaskComposite newTaskComposite) {
		super(newTaskComposite, SWT.NONE);

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		((GridData) this.getLayoutData()).widthHint=400;
		this.setLayout(new GridLayout(2, false));

		initialLabel = new Label(this, SWT.NONE);
		initialLabel.setText("No plugin selected");
	}

	public void updateSelectedPluginInfo(Plugin plugin) {
		
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (!contentsInitialized) {
					initializeContents();
					contentsInitialized=true;
				}
				updateContents(plugin);
				layout();
			}
		});
	}

	private void initializeContents() {
		if (!this.initialLabel.isDisposed()) {
			this.initialLabel.dispose();
		}

		Label label = new Label (this, SWT.NONE);
		label.setText("Plugin details");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) label.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;

		label = new Label (this, SWT.NONE);
		label.setText("Symbolic name:");
		symbolicNameLabel = new Label (this, SWT.NONE);

		label = new Label (this, SWT.NONE);
		label.setText("Version:");
		versionLabel = new Label (this, SWT.NONE);

		label = new Label (this, SWT.NONE);
		label.setText("Name:");
		nameLabel = new Label (this, SWT.NONE);

		label = new Label (this, SWT.NONE);
		label.setText("Vendor:");
		vendorLabel = new Label (this, SWT.NONE);

		label = new Label (this, SWT.NONE);
		label.setText("Description:");
		descriptionLabel = new Label (this, SWT.NONE);
	}
	
	private void updateContents(Plugin plugin) {
		this.symbolicNameLabel.setText(plugin.getSymbolicName());
		this.versionLabel.setText(plugin.getVersion());
		this.nameLabel.setText(plugin.getName());
		this.vendorLabel.setText(plugin.getVendor());
		this.descriptionLabel.setText(plugin.getDescription());
	}

}
