package ee.ut.cs.rum.example.plugin.v2.factory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginConfiguration;

public class RumPluginConfigurationImpl implements RumPluginConfiguration {

	@Override
	public void createConfigurationUi(Composite parent) {
		Label l = new Label(parent, SWT.BORDER);
		l.setText("ToyPlugin2 Configuration (TODO)");
	}

	@Override
	public Object getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
