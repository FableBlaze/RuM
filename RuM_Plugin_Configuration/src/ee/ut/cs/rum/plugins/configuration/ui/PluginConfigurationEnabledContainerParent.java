package ee.ut.cs.rum.plugins.configuration.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.database.domain.UserFile;

public abstract class PluginConfigurationEnabledContainerParent extends Composite {
	private static final long serialVersionUID = -5077432582839162951L;

	protected PluginConfigurationEnabledContainerParent(Composite parent) {
		super(parent, SWT.NONE);
		this.setLayout(new GridLayout());
	}
	
	public abstract UserFile tmpUserFileUploadedNotify(UserFile tmpUserFile);
	public abstract void taskUserFileSelectedNotify(UserFile tmpUserFile);
}
