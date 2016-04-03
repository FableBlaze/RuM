package ee.ut.cs.rum.plugins.development.ui.input;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;

public class ConfigurationItemFile extends Text implements ConfigurationItemInterface {
	private static final long serialVersionUID = -6806863065839357763L;

	public ConfigurationItemFile(Composite parent, PluginParameterFile parameterFile) {
		super(parent, SWT.BORDER);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setToolTipText(parameterFile.getDescription());
	}

	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}

}
