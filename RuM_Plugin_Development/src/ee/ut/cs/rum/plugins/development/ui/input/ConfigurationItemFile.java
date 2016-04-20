package ee.ut.cs.rum.plugins.development.ui.input;

import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;

public class ConfigurationItemFile extends Composite implements ConfigurationItemInterface {
	private static final long serialVersionUID = 3599873879215927039L;

	public ConfigurationItemFile(Composite parent, PluginParameterFile parameterFile) {
		super(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setToolTipText(parameterFile.getDescription());
		
		createContents();
	}
	
	private void createContents() {
		Combo fileSelectorCombo = new Combo(this, SWT.READ_ONLY);
		fileSelectorCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		FileUpload fileUpload = new FileUpload(this, SWT.NONE);
		fileUpload.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		fileUpload.setText("Upload");
	}

	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return "a";
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}
}
