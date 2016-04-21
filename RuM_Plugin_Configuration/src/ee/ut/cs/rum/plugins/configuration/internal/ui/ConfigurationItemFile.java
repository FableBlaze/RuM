package ee.ut.cs.rum.plugins.configuration.internal.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.rap.fileupload.DiskFileUploadReceiver;
import org.eclipse.rap.fileupload.FileUploadEvent;
import org.eclipse.rap.fileupload.FileUploadHandler;
import org.eclipse.rap.fileupload.FileUploadListener;
import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.domain.enums.SystemParameterName;
import ee.ut.cs.rum.database.util.SystemParameterAccess;
import ee.ut.cs.rum.database.util.UserFileAccess;
import ee.ut.cs.rum.plugins.configuration.internal.Activator;
import ee.ut.cs.rum.plugins.configuration.internal.util.UserFilesData;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;

public class ConfigurationItemFile extends Composite implements ConfigurationItemInterface {
	private static final long serialVersionUID = 3599873879215927039L;

	//Temporary file is always last on fileSelectorCombo
	private File temporaryFile;
	private List<UserFile> userFiles;
	private Combo fileSelectorCombo;
	private Long workspaceId;

	public ConfigurationItemFile(Composite parent, PluginParameterFile parameterFile, Long workspaceId) {
		super(parent, SWT.NONE);

		this.workspaceId=workspaceId;

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setToolTipText(parameterFile.getDescription());

		createContents();
	}

	private void createContents() {
		fileSelectorCombo = new Combo(this, SWT.READ_ONLY);
		fileSelectorCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		if (workspaceId!=null) {
			this.userFiles = UserFileAccess.getWorkspaceUserFilesDataFromDb(workspaceId);
			for (UserFile userFile : userFiles) {
				fileSelectorCombo.add(userFile.getOriginalFilename() + "  (" + new SimpleDateFormat("dd-MM-yyyy HH:MM").format(userFile.getUploadedAt()) + ")");
			}
		}

		DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();
		FileUploadHandler uploadHandler = new FileUploadHandler(receiver);
		uploadHandler.addUploadListener(new FileUploadListener() {
			@Override
			public void uploadProgress(FileUploadEvent arg0) {
			}
			@Override
			public void uploadFailed(FileUploadEvent arg0) {
			}

			@Override
			public void uploadFinished(FileUploadEvent arg0) {
				temporaryFile = receiver.getTargetFiles()[receiver.getTargetFiles().length-1];
				Activator.getLogger().info("Uploaded file: " + temporaryFile.getAbsolutePath());

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						fileSelectorCombo.add(temporaryFile.getName());
						fileSelectorCombo.select(fileSelectorCombo.getItemCount()-1);
					}
				});
			}

		});

		FileUpload fileUpload = new FileUpload(this, SWT.NONE);
		fileUpload.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		fileUpload.setText("Upload");
		fileUpload.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -7623994796399336054L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				fileSelectorCombo.deselectAll();
				if (temporaryFile != null) {
					temporaryFile=null;
					fileSelectorCombo.remove(fileSelectorCombo.getItemCount()-1);
				}
				fileUpload.submit(uploadHandler.getUploadUrl());
			}
		});
	}

	@Override
	public void setValue(String value) {
		if (value!=null && !value.equals("")) {
			if (userFiles==null) {
				UserFile userFile = UserFileAccess.getUserFileDataFromDb(Long.parseLong(value));
				fileSelectorCombo.add(userFile.getOriginalFilename() + "  (" + new SimpleDateFormat("dd-MM-yyyy HH:MM").format(userFile.getUploadedAt()) + ")");
				fileSelectorCombo.select(0);
			} else {
				for (int i = 0; i < userFiles.size(); i++) {
					if (userFiles.get(i).getId()==Long.parseLong(value)) {
						fileSelectorCombo.select(i);
					}
				}
			}
		}
	}

	@Override
	public String getValue() {
		int selectionIndex = fileSelectorCombo.getSelectionIndex();
		if (selectionIndex == -1) {
			return null;
		} else if (selectionIndex < userFiles.size()) {
			return userFiles.get(selectionIndex).getId().toString();
		} else {
			boolean copySucceeded = false;
			File upload_file_path = new File(SystemParameterAccess.getSystemParameterValue(SystemParameterName.UPLOAD_FILE_PATH));
			File destinationFile = new File(upload_file_path, new SimpleDateFormat("ddMMyyyy_HHmmssSSS_").format(new Date()));
			try {
				Files.copy( temporaryFile.toPath(), destinationFile.toPath());
				copySucceeded = true;
				Activator.getLogger().info("Copied uploaded file to: " + destinationFile.toPath());
			} catch (IOException e) {
				Activator.getLogger().info("Failed to copy uploaded file to: " + destinationFile.toPath());
			}
			
			if (copySucceeded) {
				UserFile userFile = new UserFile();
				userFile.setOriginalFilename(temporaryFile.getName());
				userFile.setUploadedBy("TODO");
				userFile.setUploadedAt(new Date());
				userFile.setWorkspaceId(workspaceId);
				userFile.setFileLocation(destinationFile.toPath().toString());
				
				userFile = UserFilesData.addUserFileDataToDb(userFile);
				
				return userFile.getId().toString();
			} else {
				return null;
			}
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}
}
