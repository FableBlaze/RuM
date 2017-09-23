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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.enums.SystemParametersEnum;
import ee.ut.cs.rum.database.util.SystemParameterAccess;
import ee.ut.cs.rum.database.util.UserAccountAccess;
import ee.ut.cs.rum.database.util.exceptions.SystemParameterNotSetException;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.plugins.internal.Activator;

public class PluginUploadDialog extends Dialog {
	private static final long serialVersionUID = 3382119816602279394L;

	private RumController rumController;
	private Shell shell;

	private File temporaryFile;
	private Plugin temporaryPlugin;

	private Label nameLabel;
	private Label symbolicNameLabel;
	private Label versionLabel;
	private Label descriptionLabel;
	private Label vendorLabel;
	private Label activatorLabel;
	private Label feedbackTextLabel;
	private Label fileNameLabel;

	private Button okButton;

	public PluginUploadDialog(Shell activeShell, RumController rumController) {
		super(activeShell, SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER | SWT.RESIZE);

		this.rumController=rumController;
	}

	public String open() {
		shell = new Shell(this.getParent(), getStyle());
		shell.setText("Add plugin");
		createContents();
		shell.pack();
		shell.setLocation (100, 100);
		shell.setMinimumSize(shell.computeSize(500, SWT.DEFAULT));
		shell.open();
		return null;
	}

	private void createContents() {
		shell.setLayout(new GridLayout());
		createManifestComposite();
		createUploadComposite();
		feedbackTextLabel = new Label(shell, SWT.NONE);
		feedbackTextLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
		createButtonsComposite();
	}

	private void createManifestComposite() {
		Composite manifestComposite  = new Composite(shell, SWT.NONE);
		manifestComposite.setLayout(new GridLayout(2, false));
		manifestComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = new Label(manifestComposite, SWT.NONE);
		label.setText("Bundle name:");
		nameLabel = new Label(manifestComposite, SWT.NONE);
		nameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		label = new Label(manifestComposite, SWT.NONE);
		label.setText("Bundle symbolic name:");
		symbolicNameLabel = new Label(manifestComposite, SWT.NONE);
		symbolicNameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		label = new Label(manifestComposite, SWT.NONE);
		label.setText("Bundle version:");
		versionLabel = new Label(manifestComposite, SWT.NONE);
		versionLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		label = new Label(manifestComposite, SWT.NONE);
		label.setText("Bundle vendor:");
		vendorLabel = new Label(manifestComposite, SWT.NONE);
		vendorLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		label = new Label(manifestComposite, SWT.NONE);
		label.setText("Bundle description:");
		descriptionLabel = new Label(manifestComposite, SWT.NONE);
		descriptionLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		label = new Label(manifestComposite, SWT.NONE);
		label.setText("Bundle activator:");
		activatorLabel = new Label(manifestComposite, SWT.NONE);
		activatorLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void createUploadComposite() {
		Composite uploadComposite  = new Composite(shell, SWT.NONE);
		uploadComposite.setLayout(new GridLayout(2, false));
		uploadComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();
		FileUploadHandler uploadHandler = new FileUploadHandler(receiver);
		uploadHandler.addUploadListener(new PluginUploadListener(receiver, this));

		FileUpload fileUpload = new FileUpload(uploadComposite, SWT.NONE);
		fileUpload.setText("Select File");
		fileUpload.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -5887356014040291468L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!fileNameLabel.isDisposed()) {
					resetValues();
					fileNameLabel.setText("");
					okButton.setEnabled(false);
				}
				fileUpload.submit(uploadHandler.getUploadUrl());
			}
		} );

		//Workaround to get filename displayed vertically in the middle
		Composite fileNameComposite = new Composite(uploadComposite, SWT.BORDER);
		fileNameComposite.setLayout(new GridLayout());
		fileNameComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileNameLabel = new Label(fileNameComposite, SWT.NONE);
		fileNameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void createButtonsComposite() {
		Composite buttonsComposite  = new Composite(shell, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(2, false));
		buttonsComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		okButton = new Button(buttonsComposite, SWT.PUSH);
		okButton.setText("OK");
		okButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		okButton.setEnabled(false);
		okButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -7891195942424898731L;

			public void widgetSelected(SelectionEvent event) {
				try {
					String plugin_path = SystemParameterAccess.getSystemParameterValue(SystemParametersEnum.PLUGIN_PATH);
					File plugin_path_file = new File(plugin_path);
					//TODO: Rule out non-unique file names
					File destinationFile = new File(plugin_path_file, new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(new Date()) + ".jar");
					Files.copy( temporaryFile.toPath(), destinationFile.toPath());
					Activator.getLogger().info("Copied uploaded plugin to: " + destinationFile.toPath());

					temporaryPlugin.setFileLocation(destinationFile.toPath().toString());
					rumController.changeData(ControllerUpdateType.CREATE, ControllerEntityType.PLUGIN, temporaryPlugin, UserAccountAccess.getGenericUserAccount());

					shell.close();
				} catch (SystemParameterNotSetException e) {
					feedbackTextLabel.setText("Plugin installing disabled");
					Activator.getLogger().info("Can not install plugins " + e.toString());
				} catch (IOException e) {
					feedbackTextLabel.setText("Plugin copy failed");
					Activator.getLogger().info("Failed to copy uploaded plugin to plugins folder");
				}
			}
		});

		Button cancel = new Button(buttonsComposite, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		cancel.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -415016060227564447L;

			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});
	}

	private void resetValues() {
		if (!symbolicNameLabel.isDisposed()) {symbolicNameLabel.setText("");} 
		if (!versionLabel.isDisposed()) {versionLabel.setText("");} 
		if (!nameLabel.isDisposed()) {nameLabel.setText("");} 
		if (!vendorLabel.isDisposed()) {vendorLabel.setText("");}
		if (!descriptionLabel.isDisposed()) {descriptionLabel.setText("");}
		if (!activatorLabel.isDisposed()) {activatorLabel.setText("");}
	}

	public void setTemporaryFile(File temporaryFile) {
		this.temporaryFile = temporaryFile;

		if (!fileNameLabel.isDisposed()) {
			//Needs syncExec because is called from PluginUploadListener
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					fileNameLabel.setText(temporaryFile.getName());
				}
			});
		}
	}

	public void setTemporaryPlugin(Plugin temporaryPlugin, String feedback) {
		this.temporaryPlugin = temporaryPlugin;

		//Needs syncExec because is called from PluginUploadListener
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (temporaryPlugin==null) {
					resetValues();
					okButton.setEnabled(false);
				} else {
					if (!symbolicNameLabel.isDisposed()) {symbolicNameLabel.setText(temporaryPlugin.getBundleSymbolicName());} 
					if (!versionLabel.isDisposed()) {versionLabel.setText(temporaryPlugin.getBundleVersion());} 
					if (!nameLabel.isDisposed()) {nameLabel.setText(temporaryPlugin.getBundleName());} 
					if (!vendorLabel.isDisposed()) {vendorLabel.setText(temporaryPlugin.getBundleVendor());}
					if (!descriptionLabel.isDisposed()) {descriptionLabel.setText(temporaryPlugin.getBundleDescription());}
					if (!activatorLabel.isDisposed()) {activatorLabel.setText(temporaryPlugin.getBundleActivator());}
					okButton.setEnabled(true);
				}
				feedbackTextLabel.setText(feedback);
			}
		});
	}

}

