package ee.ut.cs.rum.plugins.configuration.internal.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ee.ut.cs.rum.plugins.configuration.internal.Activator;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterObjectSelection;

public class ConfigurationItemObjectSelection extends Combo implements ConfigurationItemInterface {
	private static final long serialVersionUID = -4335268682134875034L;

	private String internalName;
	private String displayName;
	private boolean required;

	private List<Integer> selectionItems;

	private int selectionIndex;

	public ConfigurationItemObjectSelection(Composite pluginConfigurationUi, PluginParameterObjectSelection pluginParameterObjectSelection) {
		super(pluginConfigurationUi, SWT.READ_ONLY);

		this.internalName=pluginParameterObjectSelection.getInternalName();
		this.displayName=pluginParameterObjectSelection.getDisplayName();
		this.setToolTipText(pluginParameterObjectSelection.getDescription());
		this.required=pluginParameterObjectSelection.getRequired();

		createContents();
	}

	private void createContents() {
		this.selectionIndex=-1;

		selectionItems=new ArrayList<Integer>();

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		this.addSelectionListener(new SelectionListener() {			
			private static final long serialVersionUID = -2671867325224354752L;
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (ConfigurationItemObjectSelection.this.getSelectionIndex()==selectionIndex && event.stateMask==SWT.CTRL) {
					ConfigurationItemObjectSelection.this.deselectAll();
				}
				selectionIndex = ConfigurationItemObjectSelection.this.getSelectionIndex();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
	}
	
	public void setObjectReferences(Map<Integer, JsonObject> objectReferencesMap) {
		Gson gson = new Gson();
		for (Integer key : objectReferencesMap.keySet()) {
			this.add(gson.toJson(objectReferencesMap.get(key)));
			selectionItems.add(key);
		}
	}

	public void addObjectReference(JsonObject inputObjectInstance) {
		Gson gson = new Gson();
		this.add(gson.toJson(inputObjectInstance));
		selectionItems.add(inputObjectInstance.get("id").getAsInt());
	}

	public void removeObjectReference (int id)  {
		int indexToRemove = selectionItems.indexOf(id);
		this.remove(indexToRemove);
		selectionItems.remove(indexToRemove);
		Activator.getLogger().info("removeObject: " + id);
	}

	@Override
	public void setValue(String value) {
		this.select(selectionItems.indexOf(Integer.valueOf(value)));
		selectionIndex = ConfigurationItemObjectSelection.this.getSelectionIndex();
	}

	@Override
	public String getValue() {
		if (selectionIndex==-1) {
			return null;
		}
		return Integer.toString(selectionItems.get(selectionIndex));
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
