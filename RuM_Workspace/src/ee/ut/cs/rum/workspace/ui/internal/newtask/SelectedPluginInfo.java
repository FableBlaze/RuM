package ee.ut.cs.rum.workspace.ui.internal.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class SelectedPluginInfo extends Composite {
	private static final long serialVersionUID = 2323888161640392669L;
	
	private Label symbolicNameLabel;
	private Label versionNameLabel;
	private Label nameameLabel;
	private Label vendorLabel;
	private Label descriptionLabel;

	SelectedPluginInfo(NewTaskComposite newTaskComposite) {
		super(newTaskComposite, SWT.BORDER);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		((GridData) this.getLayoutData()).widthHint=400;
		this.setLayout(new GridLayout(2, false));
		
		Label label = new Label(this, SWT.NONE);
		label.setText("No plugin selected");
		
		//initializeContents();
	}
	
	private void initializeContents() {
		Label label = new Label (this, SWT.NONE);
		label.setText("Plugin details");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) label.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		
		label = new Label (this, SWT.NONE);
		label.setText("Symbolic name:");
		symbolicNameLabel = new Label (this, SWT.NONE);

		label = new Label (this, SWT.NONE);
		label.setText("Version:");
		versionNameLabel = new Label (this, SWT.NONE);

		label = new Label (this, SWT.NONE);
		label.setText("Name:");
		nameameLabel = new Label (this, SWT.NONE);
		
		label = new Label (this, SWT.NONE);
		label.setText("Vendor:");
		vendorLabel = new Label (this, SWT.NONE);
		
		label = new Label (this, SWT.NONE);
		label.setText("Description:");
		descriptionLabel = new Label (this, SWT.NONE);

	}

}
