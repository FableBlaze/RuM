package ee.ut.cs.rum.plugins.development.ui.input;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelectionItem;

public class ConfigurationItemSelection extends Combo implements ConfigurationItemInterface {
	private static final long serialVersionUID = -4510159560743563899L;

	public ConfigurationItemSelection(Composite parent, PluginParameterSelection parameterSelection) {
		super(parent, SWT.READ_ONLY);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setToolTipText(parameterSelection.getDescription());
		
		for (PluginParameterSelectionItem parameterSelectionItem : parameterSelection.getSelectionItems()) {
			this.add(parameterSelectionItem.getDisplayName());
		}
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
