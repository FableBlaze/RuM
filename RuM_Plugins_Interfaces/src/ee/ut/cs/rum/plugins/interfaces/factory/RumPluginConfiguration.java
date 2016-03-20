package ee.ut.cs.rum.plugins.interfaces.factory;

import org.eclipse.swt.widgets.Composite;

public interface RumPluginConfiguration {
	public void createConfigurationUi(Composite parent);
	public Object getConfigurationFromUi();
}
