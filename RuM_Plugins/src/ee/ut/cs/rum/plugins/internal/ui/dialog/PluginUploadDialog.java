package ee.ut.cs.rum.plugins.internal.ui.dialog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
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
import org.osgi.framework.ServiceReference;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.util.SystemParameterAccess;
import ee.ut.cs.rum.plugins.interfaces.RumPluginFactory;
import ee.ut.cs.rum.plugins.internal.Activator;
import ee.ut.cs.rum.plugins.internal.ui.overview.OverviewTabContents;
import ee.ut.cs.rum.plugins.internal.util.PluginsData;

public class PluginUploadDialog extends Dialog {
	private static final long serialVersionUID = 3382119816602279394L;

	ServerPushSession pushSession;

	private OverviewTabContents overviewTabContents;

	private Bundle temporaryBundle;
	private File temporaryFile;
	private Plugin temporaryPlugin;
	boolean serviceCheck;

	private Label nameValue;
	private Label symbolicNameValue;
	private Label versionValue;
	private Label descriptionValue;
	private Label vendorValue;
	private Label activatorValue;
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

		feedbackTextValue = new Label(shell, SWT.NONE);
		feedbackTextValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) feedbackTextValue.getLayoutData()).horizontalSpan = ((GridLayout) shell.getLayout()).numColumns;


		DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();
		FileUploadHandler uploadHandler = new FileUploadHandler(receiver);
		uploadHandler.addUploadListener(new FileUploadListener() {
			public void uploadProgress(FileUploadEvent event) {}
			public void uploadFailed(FileUploadEvent event) {}
			public void uploadFinished(FileUploadEvent event) {
				temporaryFile = receiver.getTargetFiles()[receiver.getTargetFiles().length-1];
				Activator.getLogger().info("Uploaded file: " + temporaryFile.getAbsolutePath());
				temporaryBundle = null;
				serviceCheck = false;
				try {
					temporaryBundle = Activator.getContext().installBundle("file:///" + temporaryFile.getAbsolutePath());
					Activator.getLogger().info("Temporary plugin loaded");

					if (temporaryBundle!=null && temporaryBundle.getSymbolicName()!=null) {
						temporaryBundle.start();
						serviceCheck = implementsRumPluginFactory(temporaryBundle);
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
						if (temporaryBundle!=null && temporaryBundle.getSymbolicName()!=null && serviceCheck) {
							temporaryPlugin = new Plugin();
							okButton.setEnabled(true);
							feedbackTextValue.setText("");
							
							for (Enumeration<String> e = temporaryBundle.getHeaders().keys(); e.hasMoreElements();) {
								Object key = e.nextElement();
								String value = temporaryBundle.getHeaders().get(key);
								if (key.equals("Bundle-SymbolicName") && !symbolicNameValue.isDisposed()) {
									symbolicNameValue.setText(value);
									temporaryPlugin.setBundleSymbolicName(value);
								} else if (key.equals("Bundle-Version") && !versionValue.isDisposed()) {
									versionValue.setText(value);
									temporaryPlugin.setBundleVersion(value);
								} else if (key.equals("Bundle-Name") && !nameValue.isDisposed()) {
									nameValue.setText(value);
									temporaryPlugin.setBundleName(value);
								} else if (key.equals("Bundle-Vendor") && !vendorValue.isDisposed()) {
									vendorValue.setText(value);
									temporaryPlugin.setBundleVendor(value);
								} else if (key.equals("Bundle-Description") && !descriptionValue.isDisposed()) {
									descriptionValue.setText(value);
									temporaryPlugin.setBundleDescription(value);
								} else if (key.equals("Bundle-Activator") && !activatorValue.isDisposed()) {
									activatorValue.setText(value);
									temporaryPlugin.setBundleActivator(value);
								} else if (key.equals("Import-Package")) {
									temporaryPlugin.setBundleImportPackage(value);
								}
							}
							temporaryPlugin.setOriginalFilename(temporaryFile.getName());
							
						} else {
							temporaryPlugin = null;
							okButton.setEnabled(false);
							feedbackTextValue.setText("The selected file is not a valid plugin");

							if (!symbolicNameValue.isDisposed()) {symbolicNameValue.setText("");} 
							if (!versionValue.isDisposed()) {versionValue.setText("");} 
							if (!nameValue.isDisposed()) {nameValue.setText("");} 
							if (!vendorValue.isDisposed()) {vendorValue.setText("");}
							if (!descriptionValue.isDisposed()) {descriptionValue.setText("");}
							if (!activatorValue.isDisposed()) {activatorValue.setText("");}
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
				boolean copySucceeded = false;
				File destinationFile = null;

				String plugin_path = SystemParameterAccess.getSystemParameterValue("plugin_path");
				if (plugin_path!=null) {
					destinationFile = new File(plugin_path + new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(new Date()) + ".jar");
					try {
						Files.copy( temporaryFile.toPath(), destinationFile.toPath());
						copySucceeded = true;
						Activator.getLogger().info("Copied uploaded plugin to: " + destinationFile.toPath());
					} catch (IOException e) {
						Activator.getLogger().info("Failed to copy uploaded plugin to: " + destinationFile.toPath());
					}
				}

				if (copySucceeded) {
					temporaryPlugin.setUploadedAt(new Date());
					temporaryPlugin.setUploadedBy("TODO"); //TODO: Add reference to the user
					temporaryPlugin.setFileLocation(destinationFile.toPath().toString());
					PluginsData.addPluginDataToDb(temporaryPlugin, overviewTabContents);
					shell.close();
				} else {
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							feedbackTextValue.setText("Plugin install failed");
						}
					});
				}
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

	private boolean implementsRumPluginFactory(Bundle temporaryBundle) {
		if (temporaryBundle.getRegisteredServices()!=null) {
			for (ServiceReference<?> serviceReference : temporaryBundle.getRegisteredServices()) {
				String[] objectClasses = (String[])serviceReference.getProperty("objectClass");
				for (String objectClass : objectClasses) {
					if (objectClass.equals(RumPluginFactory.class.getName())) {
						return true;
					}
				}	
			}
		}
		return false;
	}
}

