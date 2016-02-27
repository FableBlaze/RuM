package ee.ut.cs.rum.plugins.internal.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.ui.dialog.PluginUploadDialog;

public class PluginsOverview extends Composite{
	private static final long serialVersionUID = 6363000997779117721L;
	
	private List<Plugin> plugins;

	public PluginsOverview(OverviewTabContents overviewTabContents, List<Plugin> plugins) {
		super(overviewTabContents, SWT.NONE);
		
		this.plugins = plugins;
		
		this.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
		this.setLayout(new GridLayout(2, false));
		
		createContents();
	}
	
	
	private void createContents() {
		Label label = new Label(this, SWT.NONE);
		label.setText("Total plugins:");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));
		
		label = new Label(this, SWT.NONE);
		label.setText(Integer.toString(plugins.size()));
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));
		
		Button button = new Button(this, SWT.PUSH);
		button.setText("Add plugin (TODO)");
		GridData gridData = new GridData(SWT.CENTER, SWT.BOTTOM, true, true);
		gridData.horizontalSpan = 2;
		button.setLayoutData(gridData);

		button.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 5362888762173659283L;

			@Override
			public void handleEvent(Event arg0) {
				PluginUploadDialog pud = new PluginUploadDialog(Display.getCurrent().getActiveShell());
				pud.open();
			}
		});
	}

}
