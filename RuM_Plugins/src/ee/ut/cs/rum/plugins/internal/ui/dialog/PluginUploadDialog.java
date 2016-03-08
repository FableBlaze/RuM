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
import org.eclipse.swt.widgets.Text;
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

	private Label fileName;
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

		Text pluginName = new Text(shell, SWT.BORDER);
		pluginName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Text pluginDescription = new Text(shell, SWT.BORDER);
		pluginDescription.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();
		FileUploadHandler uploadHandler = new FileUploadHandler(receiver);
		uploadHandler.addUploadListener(new FileUploadListener() {
			public void uploadProgress(FileUploadEvent event) {}
			public void uploadFailed(FileUploadEvent event) {}
			public void uploadFinished(FileUploadEvent event) {
				Activator.getLogger().info("Uploaded file: " + receiver.getTargetFiles()[receiver.getTargetFiles().length-1].getAbsolutePath());
				Bundle b = null;

				try {
					b = Activator.getContext().installBundle("file:///" + receiver.getTargetFiles()[receiver.getTargetFiles().length-1].getAbsolutePath());
					b.start();
					b.stop();
				} catch (BundleException e1) {
					Activator.getLogger().info("Temporary plugin loading failed");
					try {
						if (b!=null) {b.uninstall();}
					} catch (BundleException e) {
						Activator.getLogger().info("Temporary plugin uninstalling failed");
					}
				}


				if (b!=null && b.getSymbolicName()!=null) {
					for (Enumeration<String> e = b.getHeaders().keys(); e.hasMoreElements();) {
						Object key = e.nextElement();
						Activator.getLogger().info("Plugin headers: " + key + " - " + b.getHeaders().get(key));
					}
				} else {
					Activator.getLogger().info("Uploaded file is not a valid plugin");
					try {
						if (b!=null) {b.uninstall();}
					} catch (BundleException e) {
						Activator.getLogger().info("Temporary plugin uninstalling failed");
					}
				}


				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						if (!fileName.isDisposed()) {
							fileName.setText(receiver.getTargetFiles()[receiver.getTargetFiles().length-1].getName());
							ok.setEnabled(true);
						}
					}
				});
			}
		} );

		FileUpload fileUpload = new FileUpload(shell, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 1;
		fileUpload.setLayoutData(gridData);
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
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(gridData);
		ok.setEnabled(false);
		ok.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -7891195942424898731L;

			public void widgetSelected(SelectionEvent event) {
				Plugin plugin = new Plugin();
				plugin.setName(pluginName.getText());
				plugin.setDescription(pluginDescription.getText());
				PluginsData.addPluginDataToDb(plugin, overviewTabContents);
				shell.close();
			}
		});

		Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		cancel.setLayoutData(gridData);
		cancel.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -415016060227564447L;

			public void widgetSelected(SelectionEvent event) {
				pushSession.stop();
				shell.close();
			}
		});
	}
}

