package ee.ut.cs.rum.plugins.configuration.internal.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelectionItem;

public class ConfigurationItemSelection extends Combo implements ConfigurationItemInterface {
	private static final long serialVersionUID = -4510159560743563899L;
	
	private String internalName;
	private String displayName;
	private boolean required;
	
	private List<PluginParameterSelectionItem> selectionItems;
	
	private int preEventSelectionIndex;

	public ConfigurationItemSelection(Composite parentComposite, PluginParameterSelection parameterSelection) {
		super(parentComposite, SWT.READ_ONLY);
		
		this.internalName=parameterSelection.getInternalName();
		this.displayName=parameterSelection.getDisplayName();
		this.setToolTipText(parameterSelection.getDescription());
		this.required=parameterSelection.getRequired();
		
		this.preEventSelectionIndex=-1;
		
		selectionItems = new ArrayList<PluginParameterSelectionItem>();

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.addSelectionListener(new SelectionListener() {			
			private static final long serialVersionUID = -2671867325224354752L;
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (ConfigurationItemSelection.this.getSelectionIndex()==preEventSelectionIndex && event.stateMask==SWT.CTRL) {
					ConfigurationItemSelection.this.deselectAll();
				}
				preEventSelectionIndex = ConfigurationItemSelection.this.getSelectionIndex();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});

		for (PluginParameterSelectionItem parameterSelectionItem : parameterSelection.getSelectionItems()) {
			this.add(parameterSelectionItem.getDisplayName());
			selectionItems.add(parameterSelectionItem);
			if (parameterSelectionItem.getInternalName().equals(parameterSelection.getDefaultValue())) {
				this.select(selectionItems.size()-1);
				this.preEventSelectionIndex=selectionItems.size()-1;
			}
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
		if (this.getSelectionIndex()!=-1) {
			return selectionItems.get(this.getSelectionIndex()).getInternalName();
		}
		return null;
	}

	@Override
	public String getInternalName() {
		return internalName;
	}
	
	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public boolean getRequired() {
		return required;
	}
}
