package ee.ut.cs.rum.plugins.development.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.plugins.development.description.PluginInfo;

public class PluginConfigurationUi extends Composite {
	private static final long serialVersionUID = -5475837154117723386L;
	
	public PluginConfigurationUi(Composite parent, PluginInfo pluginInfo) {
		super(parent, SWT.CLOSE | SWT.H_SCROLL | SWT.V_SCROLL);
		
		this.setLayout(new GridLayout(2, false));
		
		createContents(pluginInfo);
		
		this.setSize(this.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private void createContents(PluginInfo pluginInfo) {
		Label label = new Label (this, SWT.NONE);
		label.setText(pluginInfo.getName());
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) label.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;

		label = new Label (this, SWT.NONE);
		label.setText(pluginInfo.getDescription());
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) label.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		
		this.setSize(this.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

}
