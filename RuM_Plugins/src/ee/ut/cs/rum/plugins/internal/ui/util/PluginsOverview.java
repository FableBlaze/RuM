package ee.ut.cs.rum.plugins.internal.ui.util;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.Plugin;

public final class PluginsOverview {
	private PluginsOverview() {
	}
	
	public static void createPluginsOverview(Composite parent, List<Plugin> plugins) {
		Composite pluginsOverview = new Composite(parent, SWT.NONE);
		pluginsOverview.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
		pluginsOverview.setLayout(new GridLayout(2, false));
		
		Label label = new Label(pluginsOverview, SWT.NONE);
		label.setText("Total plugins:");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));
		
		label = new Label(pluginsOverview, SWT.NONE);
		label.setText(Integer.toString(plugins.size()));
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));
		
		Button button = new Button(pluginsOverview, SWT.PUSH);
		button.setText("Add plugin (TODO)");
		GridData gridData = new GridData(SWT.CENTER, SWT.BOTTOM, true, true);
		gridData.horizontalSpan = 2;
		button.setLayoutData(gridData);
	}
	
}
