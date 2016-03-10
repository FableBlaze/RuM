package ee.ut.cs.rum.plugins.internal.ui.dialog;

import java.io.File;
import java.util.Date;
import java.util.Enumeration;

import org.eclipse.rap.fileupload.DiskFileUploadReceiver;
import org.eclipse.rap.fileupload.FileUploadEvent;
import org.eclipse.rap.fileupload.FileUploadHandler;
import org.eclipse.rap.fileupload.FileUploadListener;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.Activator;
import ee.ut.cs.rum.plugins.internal.ui.OverviewTabContents;
import ee.ut.cs.rum.plugins.internal.util.PluginsData;

public class PluginUploadDialog extends Dialog {
	private static final long serialVersionUID = 3382119816602279394L;

	ServerPushSession pushSession;

	private OverviewTabContents overviewTabContents;

	private Bundle temporaryBundle;
	private File temporaryFile;

	private Label nameValue;
	private Label symbolicNameValue;
	private Label versionValue;
	private Label descriptionValue;
	private Label vendorValue;
	private Label activatorValue;
	private Label importPackageValue;
	private Label feedbackTextValue;
	private Label fileName;

	private Button okButton;

	public PluginUploadDialog(Shell activeShell, OverviewTabContents overviewTabContents) {
		super(activeShell, SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER);
		this.overviewTabContents = overviewTabContents;
	}

	public String open() {
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText("Add plugin");
		createContents(shell);
		shell.pack();
		shell.setLocation (100, 100);
		shell.open();
		pushSession = new ServerPushSession();
		pushSession.start();
		return null;
	}

	private void createContents(final Shell shell) {
		shell.setLayout(new GridLayout(2, true));

		Label bundleNameLabel = new Label(shell, SWT.NONE);
		bundleNameLabel.setText("Bundle name:");
		nameValue = new Label(shell, SWT.NONE);
		nameValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bundleSymbolicNameLabel = new Label(shell, SWT.NONE);
		bundleSymbolicNameLabel.setText("Bundle symbolic name:");
		symbolicNameValue = new Label(shell, SWT.NONE);
		symbolicNameValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bundleVersionLabel = new Label(shell, SWT.NONE);
		bundleVersionLabel.setText("Bundle version:");
		versionValue = new Label(shell, SWT.NONE);
		versionValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label vendorLabel = new Label(shell, SWT.NONE);
		vendorLabel.setText("Bundle vendor:");
		vendorValue = new Label(shell, SWT.NONE);
		vendorValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label descriptionLabel = new Label(shell, SWT.NONE);
		descriptionLabel.setText("Bundle description:");
		descriptionValue = new Label(shell, SWT.NONE);
		descriptionValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bundleActivatorLabel = new Label(shell, SWT.NONE);
		bundleActivatorLabel.setText("Bundle activator:");
		activatorValue = new Label(shell, SWT.NONE);
		activatorValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bundleImportPackageLabel = new Label(shell, SWT.NONE);
		bundleImportPackageLabel.setText("Bundle imported packages:");
		importPackageValue = new Label(shell, SWT.NONE);
		importPackageValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		feedbackTextValue = new Label(shell, SWT.NONE);
		feedbackTextValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) feedbackTextValue.getLayoutData()).horizontalSpan = 2;


		DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();
		FileUploadHandler uploadHandler = new FileUploadHandler(receiver);
		uploadHandler.addUploadListener(new FileUploadListener() {
			public void uploadProgress(FileUploadEvent event) {}
			public void uploadFailed(FileUploadEvent event) {}
			public void uploadFinished(FileUploadEvent event) {
				temporaryFile = receiver.getTargetFiles()[receiver.getTargetFiles().length-1];
				Activator.getLogger().info("Uploaded file: " + temporaryFile.getAbsolutePath());
				temporaryBundle = null;

				try {
					temporaryBundle = Activator.getContext().installBundle("file:///" + temporaryFile.getAbsolutePath());
					Activator.getLogger().info("Temporary plugin loaded");

					if (temporaryBundle!=null && temporaryBundle.getSymbolicName()!=null) {
						temporaryBundle.start();
						temporaryBundle.stop();
						Activator.getLogger().info("Temporary plugin initial start/stop done");
					} else {
						Activator.getLogger().error("Uploaded file is not a valid plugin");
					}
				} catch (BundleException e1) {
					Activator.getLogger().error("Temporary plugin loading failed");
				}

				//TODO: Consider refactoring this part of the code
				Display.getDefault().syncExec(new Runnable() {
					public void run() {

						//TODO: Check for duplicates
						if (temporaryBundle!=null && temporaryBundle.getSymbolicName()!=null) {
							okButton.setEnabled(true);
							feedbackTextValue.setText("");

							for (Enumeration<String> e = temporaryBundle.getHeaders().keys(); e.hasMoreElements();) {
								Object key = e.nextElement();
								if (key.equals("Bundle-SymbolicName") && !symbolicNameValue.isDisposed()) {
									symbolicNameValue.setText(temporaryBundle.getHeaders().get(key));
								} else if (key.equals("Bundle-Version") && !versionValue.isDisposed()) {
									versionValue.setText(temporaryBundle.getHeaders().get(key));
								} else if (key.equals("Bundle-Name") && !nameValue.isDisposed()) {
									nameValue.setText(temporaryBundle.getHeaders().get(key));
								} else if (key.equals("Bundle-Vendor") && !vendorValue.isDisposed()) {
									vendorValue.setText(temporaryBundle.getHeaders().get(key));
								} else if (key.equals("Bundle-Description") && !descriptionValue.isDisposed()) {
									descriptionValue.setText(temporaryBundle.getHeaders().get(key));
								} else if (key.equals("Bundle-Activator") && !activatorValue.isDisposed()) {
									activatorValue.setText(temporaryBundle.getHeaders().get(key));
								} else if (key.equals("Import-Package") && !importPackageValue.isDisposed()) {
									importPackageValue.setText(temporaryBundle.getHeaders().get(key));
								}
							}
						} else {
							okButton.setEnabled(false);
							feedbackTextValue.setText("The selected file is not a valid plugin");

							if (!symbolicNameValue.isDisposed()) {symbolicNameValue.setText("");} 
							if (!versionValue.isDisposed()) {versionValue.setText("");} 
							if (!nameValue.isDisposed()) {nameValue.setText("");} 
							if (!vendorValue.isDisposed()) {vendorValue.setText("");}
							if (!descriptionValue.isDisposed()) {descriptionValue.setText("");}
							if (!activatorValue.isDisposed()) {activatorValue.setText("");}
							if (!importPackageValue.isDisposed()) {importPackageValue.setText("");}
						}

						if (!fileName.isDisposed()) {
							fileName.setText(temporaryFile.getName());
						}
					}
				});

				if (temporaryBundle!=null) {
					try {
						temporaryBundle.uninstall();
						Activator.getLogger().error("Temporary plugin uninstalled");
					} catch (BundleException e) {
						Activator.getLogger().error("Temporary plugin uninstalling failed");
					}
				}
			}
		} );

		FileUpload fileUpload = new FileUpload(shell, SWT.NONE);
		fileUpload.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileUpload.setText("Select File");
		fileUpload.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5887356014040291468L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				fileUpload.submit(uploadHandler.getUploadUrl());

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						if (!fileName.isDisposed()) {
							fileName.setText("");
							okButton.setEnabled(false);
						}
					}
				});
			}
		} );

		fileName = new Label(shell, SWT.NONE);
		fileName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		//okButton should be enabled only when a valid plugin is uploaded
		okButton = new Button(shell, SWT.PUSH);
		okButton.setText("OK");
		okButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		okButton.setEnabled(false);
		okButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -7891195942424898731L;

			public void widgetSelected(SelectionEvent event) {
				Plugin plugin = new Plugin();
				plugin.setSymbolicName(symbolicNameValue.getText());
				plugin.setVersion(versionValue.getText());
				plugin.setName(nameValue.getText());
				plugin.setVendor(vendorValue.getText());
				plugin.setDescription(descriptionValue.getText());
				plugin.setActivator(activatorValue.getText());
				plugin.setImportPackage(importPackageValue.getText());
				plugin.setOriginalFilename(temporaryFile.getName());
				plugin.setUploadedAt(new Date());
				plugin.setUploadedBy("TODO"); //TODO: Add reference to the user
				PluginsData.addPluginDataToDb(plugin, overviewTabContents);
				shell.close();
			}
		});

		Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cancel.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -415016060227564447L;

			public void widgetSelected(SelectionEvent event) {
				pushSession.stop();
				shell.close();
			}
		});
	}
}

