package ee.ut.cs.rum.plugins.configuration.internal.ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ee.ut.cs.rum.plugins.configuration.internal.ui.dialog.ObjectInputDialog;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationUi;
import ee.ut.cs.rum.plugins.development.description.PluginInputObject;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterObjectList;

public class ConfigurationItemObjectList extends Composite implements ConfigurationItemInterface {
	private static final long serialVersionUID = -6538283686156068894L;
	
	private String internalName;
	private String displayName;
	private boolean required;
	private PluginInputObject pluginInputObject;
	
	private Map<Integer, JsonObject> inputObjectInstances;
	
	private Composite objectsComposite;
	
	public ConfigurationItemObjectList(PluginConfigurationUi pluginConfigurationUi, PluginParameterObjectList parameterObjectList, PluginInputObject pluginInputObject) {
		super(pluginConfigurationUi, SWT.NONE);
		
		this.internalName=parameterObjectList.getInternalName();
		this.displayName=parameterObjectList.getDisplayName();
		this.setToolTipText(parameterObjectList.getDescription());
		this.required=parameterObjectList.getRequired();
		this.pluginInputObject=pluginInputObject;
		
		inputObjectInstances = new HashMap<Integer, JsonObject>();
		
		this.setLayout(new GridLayout());
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		createContents();
	}
	

	private void createContents() {
		objectsComposite = new Composite(this, SWT.BORDER);
		objectsComposite.setLayout(new GridLayout(2, false));
		objectsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		ObjectInputDialog objectInputDialog = new ObjectInputDialog(Display.getCurrent().getActiveShell(), this, pluginInputObject);
		
		Button addObjectButton = new Button(this, SWT.PUSH);
		addObjectButton.setText("Add");
		addObjectButton.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		addObjectButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 8589018096954211029L;

			@Override
			public void handleEvent(Event arg0) {
				objectInputDialog.open();
			}
		});
	}
	
	public void addInputObjectInstance (JsonElement inputObjectValues) {
		int instanceIndex;
		if (inputObjectInstances.isEmpty()) {
			instanceIndex=0;
		} else {
			instanceIndex=Collections.max(inputObjectInstances.keySet())+1;
		}
		
		JsonObject inputObjectInstance = new JsonObject();
		inputObjectInstance.addProperty("id", instanceIndex);
		inputObjectInstance.add("values", inputObjectValues);
		
		inputObjectInstances.put(instanceIndex, inputObjectInstance);
		
		displayInputObjectInstance(inputObjectInstance);
	}
	
	private void displayInputObjectInstance(JsonObject inputObjectInstance) {
		Label label = new Label(objectsComposite, SWT.NONE);
		Gson gson = new Gson();
		label.setText(gson.toJson(inputObjectInstance));
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Button button = new Button(objectsComposite, SWT.PUSH);
		button.setText("X");
		button.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -2567782900203073741L;
			
			public void widgetSelected(SelectionEvent event) {
				inputObjectInstances.remove(inputObjectInstance.getAsJsonObject().get("id").getAsInt());
				label.dispose();
				button.dispose();
				objectsComposite.layout();
			}
		});
		objectsComposite.layout();
	}

	@Override
	public void setValue(String value) {
		JsonParser parser = new JsonParser();
		JsonElement valueJsonElement = parser.parse(value);
		for (JsonElement jsonElement : valueJsonElement.getAsJsonArray()) {
			JsonObject inputObjectInstance = jsonElement.getAsJsonObject().get("values").getAsJsonObject();
			inputObjectInstances.put(jsonElement.getAsJsonObject().get("id").getAsInt(), inputObjectInstance);
			displayInputObjectInstance(jsonElement.getAsJsonObject());
		}
	}

	@Override
	public String getValue() {
		if (inputObjectInstances.isEmpty()) {
			return null;
		} else {
			Gson gson = new Gson();
			return gson.toJson(inputObjectInstances.values().toArray());
		}
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
