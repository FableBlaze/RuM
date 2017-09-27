package ee.ut.cs.rum.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.administration.ui.SystemAdministrationUI;
import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.files.ui.FilesManagementUI;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

import org.eclipse.swt.widgets.Button;

public class RumHeader extends Composite {
	private static final long serialVersionUID = -7229992311867297026L;
	
	private RumUI rumUI;
	
	public RumHeader(Composite parent, RumController rumController, RumUI rumUI) {
		super(parent, SWT.NONE);
		
		this.rumUI=rumUI;
		
		this.setLayout(new GridLayout(7, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		Label label = new Label(this, SWT.NONE);
		label.setText("RuM");
		
		createNavigationButtons();
	}
	
	private void createNavigationButtons() {
		Button button = new Button(this, SWT.PUSH);
		button.setText("Workspace");
		button.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -7714989063610717365L;

			public void widgetSelected( SelectionEvent event ) {
				rumUI.setVisibleSection(WorkspaceUI.SECTION_NAME);
			}
		});
		
		button = new Button(this, SWT.PUSH);
		button.setText("Files");
		button.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -7714989063610717365L;

			public void widgetSelected( SelectionEvent event ) {
				rumUI.setVisibleSection(FilesManagementUI.SECTION_NAME);
			}
		});
		
		button = new Button(this, SWT.PUSH);
		button.setText("Plugins management");
		button.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -7714989063610717365L;

			public void widgetSelected( SelectionEvent event ) {
				rumUI.setVisibleSection(PluginsManagementUI.SECTION_NAME);
			}
		});
		
		button = new Button(this, SWT.PUSH);
		button.setText("System administration");
		button.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -7714989063610717365L;

			public void widgetSelected( SelectionEvent event ) {
				rumUI.setVisibleSection(SystemAdministrationUI.SECTION_NAME);
			}
		});
		
	}
}
