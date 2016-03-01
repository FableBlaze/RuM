package ee.ut.cs.rum;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class RumUI extends AbstractEntryPoint {
	private static final long serialVersionUID = 1282027955721012064L;

	public void createContents(Composite parent) {
		Composite buttonArea = new Composite(parent, SWT.NONE);
		buttonArea.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		buttonArea.setLayout(new GridLayout(2, false));

		Button workspaceButton = new Button(buttonArea, SWT.PUSH);
		workspaceButton.setText("Workspace");

		Button pluginsManagemenButton = new Button(buttonArea, SWT.PUSH);
		pluginsManagemenButton.setText("Plugins management");

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		StackLayout stackLayout = new StackLayout();
		container.setLayout(stackLayout);

		Composite workspaceSection = new Composite(container, SWT.BORDER);
		workspaceSection.setLayout(new GridLayout(2, false));
		Label l = new Label(workspaceSection, SWT.NONE);
		l.setText("Workspace contents (TODO)");
		
		Composite pluginsManagementSection = new PluginsManagementUI(container);
		
		stackLayout.topControl = workspaceSection;
		container.layout();


		workspaceButton.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -7714989063610717365L;

			public void widgetSelected( SelectionEvent event ) {
				stackLayout.topControl = workspaceSection;
				container.layout();
			}
		});

		pluginsManagemenButton.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = 307591838016942982L;

			public void widgetSelected( SelectionEvent event ) {
				stackLayout.topControl = pluginsManagementSection;
				container.layout();
			}
		});

		Activator.getLogger().info("Someone opened ee.ut.cs.rum.RumUI");
	}

}
