package ee.ut.cs.rum.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;

import org.eclipse.swt.widgets.Button;

public class NavigationMenu extends Composite {
	private static final long serialVersionUID = -7229992311867297026L;
	
	private RumUI rumUI;
	
	public NavigationMenu(Composite parent, RumController rumController, RumUI rumUI) {
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
				Composite sectionContainer = rumUI.getSectionContainer();
				((StackLayout)sectionContainer.getLayout()).topControl = rumUI.getWorkspaceSection();
				sectionContainer.layout();
			}
		});
		
		button = new Button(this, SWT.PUSH);
		button.setText("Files");
		button.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -7714989063610717365L;

			public void widgetSelected( SelectionEvent event ) {
				Composite sectionContainer = rumUI.getSectionContainer();
				((StackLayout)sectionContainer.getLayout()).topControl = rumUI.getFilesSection();
				sectionContainer.layout();
			}
		});
		
		button = new Button(this, SWT.PUSH);
		button.setText("Plugins management");
		button.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -7714989063610717365L;

			public void widgetSelected( SelectionEvent event ) {
				Composite sectionContainer = rumUI.getSectionContainer();
				((StackLayout)sectionContainer.getLayout()).topControl = rumUI.getPluginsManagementSection();
				sectionContainer.layout();
			}
		});
		
		button = new Button(this, SWT.PUSH);
		button.setText("System administration");
		button.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -7714989063610717365L;

			public void widgetSelected( SelectionEvent event ) {
				Composite sectionContainer = rumUI.getSectionContainer();
				((StackLayout)sectionContainer.getLayout()).topControl = rumUI.getSystemAdministrationSection();
				sectionContainer.layout();
			}
		});
		
		button = new Button(this, SWT.PUSH);
		button.setText("username");
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		button.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -7714989063610717365L;

			public void widgetSelected( SelectionEvent event ) {
				Composite sectionContainer = rumUI.getSectionContainer();
				((StackLayout)sectionContainer.getLayout()).topControl = rumUI.getAccountDetailsSection();
				sectionContainer.layout();
			}
		});
		
		button = new Button(this, SWT.PUSH);
		button.setText("New updates: 0"); //TODO: Update notifications counter
		button.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -7714989063610717365L;

			public void widgetSelected( SelectionEvent event ) {
				//TODO: Open notification overview
			}
		});
	}
}
