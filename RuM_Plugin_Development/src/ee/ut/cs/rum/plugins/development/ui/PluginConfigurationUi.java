package ee.ut.cs.rum.plugins.development.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameter;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterDouble;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterInteger;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterSelection;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterString;

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
		
		for (PluginParameter pluginParameter : pluginInfo.getParameters()) {
			
			label = new Label (this, SWT.NONE);
			label.setText(pluginParameter.getDisplayName());
			
			switch (pluginParameter.getParameterType()) {
			case STRING:
				PluginParameterString pluginParameterString = (PluginParameterString) pluginParameter;
				Text stringInput = new Text(this, SWT.BORDER);
				stringInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				stringInput.setText(pluginParameterString.getDefaultValue());
				stringInput.setToolTipText(pluginParameterString.getDescription());
				break; 
			case INTEGER:
				PluginParameterInteger parameterInteger = (PluginParameterInteger) pluginParameter;
				Text integerInput = new Text(this, SWT.BORDER);
				integerInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				integerInput.setText(Integer.toString(parameterInteger.getDefaultValue()));
				integerInput.setToolTipText(parameterInteger.getDescription());
				break; 
			case DOUBLE:
				PluginParameterDouble parameterDouble = (PluginParameterDouble) pluginParameter;
				Text doubleInput = new Text(this, SWT.BORDER);
				doubleInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				doubleInput.setText(Double.toString(parameterDouble.getDefaultValue()));
				doubleInput.setToolTipText(parameterDouble.getDescription());
				break; 
			case SELECTION:
				PluginParameterSelection parameterSelection = (PluginParameterSelection) pluginParameter;
				Text selectionInput = new Text(this, SWT.BORDER);
				selectionInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				selectionInput.setToolTipText(parameterSelection.getDescription());
				break; 
			case FILE:
				PluginParameterFile parameterFile = (PluginParameterFile) pluginParameter;
				Text fileInput = new Text(this, SWT.BORDER);
				fileInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				fileInput.setToolTipText(parameterFile.getDescription());
				break; 
			default:
				break;
			}
		}
	}

}
