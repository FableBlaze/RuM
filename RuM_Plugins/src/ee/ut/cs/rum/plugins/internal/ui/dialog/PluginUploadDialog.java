package ee.ut.cs.rum.plugins.internal.ui.dialog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.rap.fileupload.DiskFileUploadReceiver;
import org.eclipse.rap.fileupload.FileUploadHandler;
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

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.enums.SystemParameterName;
import ee.ut.cs.rum.database.util.SystemParameterAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.plugins.internal.Activator;

public class PluginUploadDialog extends Dialog {
	private static final long serialVersionUID = 3382119816602279394L;

	private RumController rumController;

	private File temporaryFile;
	private Plugin temporaryPlugin;

	private Label nameValue;
	private Label symbolicNameValue;
	private Label versionValue;
	private Label descriptionValue;
	private Label vendorValue;
	private Label activatorValue;
	private Label feedbackTextValue;
	private Label fileName;

	private Button okButton;

	public PluginUploadDialog(Shell activeShell, RumController rumController) {
		super(activeShell, SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER);
		
		this.rumController=rumController;
	}

	public String open() {
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText("Add plugin");
		createContents(shell);
		shell.pack();
		shell.setLocation (100, 100);
		shell.open();
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
		uploadHandler.addUploadListener(new PluginUploadListener(receiver, this));

		FileUpload fileUpload = new FileUpload(shell, SWT.NONE);
		fileUpload.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileUpload.setText("Select File");
		fileUpload.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5887356014040291468L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!fileName.isDisposed()) {
					resetValues();
					fileName.setText("");
					okButton.setEnabled(false);
				}
				fileUpload.submit(uploadHandler.getUploadUrl());
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

				String plugin_path = SystemParameterAccess.getSystemParameterValue(SystemParameterName.PLUGIN_PATH);
				if (plugin_path!=null) {
					File plugin_path_file = new File(plugin_path);
					destinationFile = new File(plugin_path_file, new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(new Date()) + ".jar");
					try {
						Files.copy( temporaryFile.toPath(), destinationFile.toPath());
						copySucceeded = true;
						Activator.getLogger().info("Copied uploaded plugin to: " + destinationFile.toPath());
					} catch (IOException e) {
						Activator.getLogger().info("Failed to copy uploaded plugin to: " + destinationFile.toPath());
					}
					
					if (copySucceeded) {
						temporaryPlugin.setFileLocation(destinationFile.toPath().toString());
						rumController.changeData(ControllerUpdateType.CREATE, ControllerEntityType.PLUGIN, temporaryPlugin, "TODO");
						
						shell.close();
					} else {
						feedbackTextValue.setText("Plugin install failed");
					}
					
				} else {
					feedbackTextValue.setText("Plugin installing disabled");
				}
			}
		});

		Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cancel.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -415016060227564447L;

			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});
	}

	private void resetValues() {
		if (!symbolicNameValue.isDisposed()) {symbolicNameValue.setText("");} 
		if (!versionValue.isDisposed()) {versionValue.setText("");} 
		if (!nameValue.isDisposed()) {nameValue.setText("");} 
		if (!vendorValue.isDisposed()) {vendorValue.setText("");}
		if (!descriptionValue.isDisposed()) {descriptionValue.setText("");}
		if (!activatorValue.isDisposed()) {activatorValue.setText("");}
	}

	public void setTemporaryFile(File temporaryFile) {
		this.temporaryFile = temporaryFile;
		
		if (!fileName.isDisposed()) {
			//Needs syncExec because is called from PluginUploadListener
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					fileName.setText(temporaryFile.getName());
				}
			});
		}
	}

	public void setTemporaryPlugin(Plugin temporaryPlugin) {
		this.temporaryPlugin = temporaryPlugin;
		
		//Needs syncExec because is called from PluginUploadListener
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (temporaryPlugin==null) {
					resetValues();
					okButton.setEnabled(false);
					feedbackTextValue.setText("The selected file is not a valid plugin");
				} else {
					if (!symbolicNameValue.isDisposed()) {symbolicNameValue.setText(temporaryPlugin.getBundleSymbolicName());} 
					if (!versionValue.isDisposed()) {versionValue.setText(temporaryPlugin.getBundleVersion());} 
					if (!nameValue.isDisposed()) {nameValue.setText(temporaryPlugin.getBundleName());} 
					if (!vendorValue.isDisposed()) {vendorValue.setText(temporaryPlugin.getBundleVendor());}
					if (!descriptionValue.isDisposed()) {descriptionValue.setText(temporaryPlugin.getBundleDescription());}
					if (!activatorValue.isDisposed()) {activatorValue.setText(temporaryPlugin.getBundleActivator());}

					okButton.setEnabled(true);
					feedbackTextValue.setText("");
				}
			}
		});
	}

}

