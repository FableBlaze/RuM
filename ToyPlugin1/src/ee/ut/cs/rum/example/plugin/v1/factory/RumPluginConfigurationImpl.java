package ee.ut.cs.rum.example.plugin.v1.factory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.example.plugin.v1.Activator;
import ee.ut.cs.rum.plugins.interfaces.factory.RumPluginConfiguration;

public class RumPluginConfigurationImpl implements RumPluginConfiguration {
	private Text t;

	@Override
	public void createConfigurationUi(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		Label l = new Label(parent, SWT.BORDER);
		l.setText("ToyPlugin1 Configuration (TODO)");
		t = new Text(parent, SWT.BORDER);
	}

	@Override
	public Object getConfigurationFromUi() {
		Activator.getLogger().info("RuM_ToyPlugin1 returned parameter: " + t.getText());
		return t.getText();
	}
}
