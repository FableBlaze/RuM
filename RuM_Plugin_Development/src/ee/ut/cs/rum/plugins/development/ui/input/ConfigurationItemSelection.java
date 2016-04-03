package ee.ut.cs.rum.plugins.development.ui.input;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelectionItem;

public class ConfigurationItemSelection extends Combo implements ConfigurationItemInterface {
	private static final long serialVersionUID = -4510159560743563899L;

	private List<PluginParameterSelectionItem> selectionItems;

	public ConfigurationItemSelection(Composite parent, PluginParameterSelection parameterSelection) {
		super(parent, SWT.READ_ONLY);
		
		selectionItems = new ArrayList<PluginParameterSelectionItem>();

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setToolTipText(parameterSelection.getDescription());

		for (PluginParameterSelectionItem parameterSelectionItem : parameterSelection.getSelectionItems()) {
			this.add(parameterSelectionItem.getDisplayName());
			selectionItems.add(parameterSelectionItem);
		}
	}

	@Override
	public void setValue(String value) {
		for (int i = 0; i < selectionItems.size(); i++) {
			if (selectionItems.get(i).getInternalName().equals(value)) {
				this.select(i);
			}
		}
	}

	@Override
	public String getValue() {
		return selectionItems.get(this.getSelectionIndex()).getInternalName();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}

}
