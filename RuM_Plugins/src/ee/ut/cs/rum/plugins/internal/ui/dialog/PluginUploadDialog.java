package ee.ut.cs.rum.plugins.internal.ui.dialog;

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

	private Label bundleNameValue;
	private Label bundleSymbolicNameValue;
	private Label bundleVersionValue;
	private Label bundleActivatorValue;
	private Label bundleImportPackageValue;
	private Label feedbackTextValue;
	private Label fileName;
	private Bundle bundle;
	private Button ok;

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
		bundleNameValue = new Label(shell, SWT.NONE);
		bundleNameValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bundleSymbolicNameLabel = new Label(shell, SWT.NONE);
		bundleSymbolicNameLabel.setText("Bundle symbolic name:");
		bundleSymbolicNameValue = new Label(shell, SWT.NONE);
		bundleSymbolicNameValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bundleVersionLabel = new Label(shell, SWT.NONE);
		bundleVersionLabel.setText("Bundle version:");
		bundleVersionValue = new Label(shell, SWT.NONE);
		bundleVersionValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bundleActivatorLabel = new Label(shell, SWT.NONE);
		bundleActivatorLabel.setText("Bundle activator:");
		bundleActivatorValue = new Label(shell, SWT.NONE);
		bundleActivatorValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label bundleImportPackageLabel = new Label(shell, SWT.NONE);
		bundleImportPackageLabel.setText("Bundle imported packages:");
		bundleImportPackageValue = new Label(shell, SWT.NONE);
		bundleImportPackageValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		feedbackTextValue = new Label(shell, SWT.NONE);
		feedbackTextValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) feedbackTextValue.getLayoutData()).horizontalSpan = 2;
		
		
		DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();
		FileUploadHandler uploadHandler = new FileUploadHandler(receiver);
		uploadHandler.addUploadListener(new FileUploadListener() {
			public void uploadProgress(FileUploadEvent event) {}
			public void uploadFailed(FileUploadEvent event) {}
			public void uploadFinished(FileUploadEvent event) {
				Activator.getLogger().info("Uploaded file: " + receiver.getTargetFiles()[receiver.getTargetFiles().length-1].getAbsolutePath());
				bundle = null;

				try {
					bundle = Activator.getContext().installBundle("file:///" + receiver.getTargetFiles()[receiver.getTargetFiles().length-1].getAbsolutePath());
					bundle.start();
					bundle.stop();
				} catch (BundleException e1) {
					Activator.getLogger().info("Temporary plugin loading failed");
					try {
						if (bundle!=null) {bundle.uninstall();}
					} catch (BundleException e) {
						Activator.getLogger().info("Temporary plugin uninstalling failed");
					}
				}


				if (bundle==null || bundle.getSymbolicName()==null) {
					Activator.getLogger().info("Uploaded file is not a valid plugin");
					try {
						if (bundle!=null) {bundle.uninstall();}
					} catch (BundleException e) {
						Activator.getLogger().info("Temporary plugin uninstalling failed");
					}
				}

				//TODO: Consider refactoring this part of the code
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						
						if (bundle!=null && bundle.getSymbolicName()!=null) {
							ok.setEnabled(true);
							feedbackTextValue.setText("");
							
							for (Enumeration<String> e = bundle.getHeaders().keys(); e.hasMoreElements();) {
								Object key = e.nextElement();
								if (key.equals("Bundle-SymbolicName") && !bundleSymbolicNameValue.isDisposed()) {
									bundleSymbolicNameValue.setText(bundle.getHeaders().get(key));
								} else if (key.equals("Bundle-Version") && !bundleVersionValue.isDisposed()) {
									bundleVersionValue.setText(bundle.getHeaders().get(key));
								} else if (key.equals("Bundle-Name") && !bundleNameValue.isDisposed()) {
									bundleNameValue.setText(bundle.getHeaders().get(key));
								} else if (key.equals("Bundle-Activator") && !bundleActivatorValue.isDisposed()) {
									bundleActivatorValue.setText(bundle.getHeaders().get(key));
								} else if (key.equals("Import-Package") && !bundleImportPackageValue.isDisposed()) {
									bundleImportPackageValue.setText(bundle.getHeaders().get(key));
								}
							}
						} else {
							ok.setEnabled(false);
							feedbackTextValue.setText("The selected file is not a valid plugin");
							
							if (!bundleSymbolicNameValue.isDisposed()) {bundleSymbolicNameValue.setText("");} 
							if (!bundleVersionValue.isDisposed()) {bundleVersionValue.setText("");} 
							if (!bundleNameValue.isDisposed()) {bundleNameValue.setText("");} 
							if (!bundleActivatorValue.isDisposed()) {bundleActivatorValue.setText("");} 
							if (!bundleImportPackageValue.isDisposed()) {bundleImportPackageValue.setText("");}
						}
						
						if (!fileName.isDisposed()) {
							fileName.setText(receiver.getTargetFiles()[receiver.getTargetFiles().length-1].getName());
						}
					}
				});

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
							ok.setEnabled(false);
						}
					}
				});
			}
		} );

		fileName = new Label(shell, SWT.NONE);
		fileName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));


		ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		ok.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ok.setEnabled(false);
		ok.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -7891195942424898731L;

			public void widgetSelected(SelectionEvent event) {
				Plugin plugin = new Plugin();
				plugin.setName("Name");
				plugin.setDescription("Description");
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

