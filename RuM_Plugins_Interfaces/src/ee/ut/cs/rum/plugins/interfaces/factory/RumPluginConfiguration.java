package ee.ut.cs.rum.plugins.interfaces.factory;

import org.eclipse.swt.widgets.Composite;

public interface RumPluginConfiguration {
	public void createConfigurationUi(Composite parent);
	public void setConfigurationUiEnabled(boolean enabled);
	public Object getConfiguration();
	public void setConfiguration(Object configuration);
}
