package ee.ut.cs.rum.plugins.configuration.internal.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.domain.UserFileType;
import ee.ut.cs.rum.database.domain.enums.SystemParameterName;
import ee.ut.cs.rum.database.util.SystemParameterAccess;
import ee.ut.cs.rum.database.util.UserFileAccess;
import ee.ut.cs.rum.plugins.configuration.internal.Activator;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;

public class ConfigurationItemFile extends Composite implements ConfigurationItemInterface {
	private static final long serialVersionUID = 3599873879215927039L;

	//Temporary file is always last on fileSelectorCombo
	private File temporaryFile;
	private List<UserFile> userFiles;
	private Combo fileSelectorCombo;
	private Long projectId;
	private File user_file_path;
	PluginParameterFile parameterFile;

	public ConfigurationItemFile(Composite parent, PluginParameterFile parameterFile, Long workspaceId) {
		super(parent, SWT.NONE);

		this.projectId=workspaceId;
		this.parameterFile = parameterFile;
		String user_file_path_asString = SystemParameterAccess.getSystemParameterValue(SystemParameterName.USER_FILE_PATH);
		if (user_file_path_asString!=null) {
			user_file_path = new File(user_file_path_asString);
		}

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

		if (projectId!=null) {
			this.userFiles = UserFileAccess.getProjectUserFilesDataFromDb(projectId);
			for (UserFile userFile : userFiles) {
				fileSelectorCombo.add(userFile.getOriginalFilename() + "  (" + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(userFile.getCreatedAt()) + ")");
			}
		}

		if (user_file_path==null && projectId!=null) {
			Label label = new Label(this, SWT.NONE);
			label.setText("File upload disabled!");
		} else {
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
	}

	@Override
	public void setValue(String value) {
		if (value!=null && !value.equals("")) {
			if (userFiles==null) {
				UserFile userFile = UserFileAccess.getUserFileDataFromDb(value);
				if (userFile!=null) {
					fileSelectorCombo.add(userFile.getOriginalFilename() + "  (" + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(userFile.getCreatedAt()) + ")");
					fileSelectorCombo.select(0);	
				}
			} else {
				for (int i = 0; i < userFiles.size(); i++) {
					if (userFiles.get(i).getFileLocation().equals(value)) {
						fileSelectorCombo.select(i);
					}
				}
			}
		}
	}

	@Override
	public String getValue() {
		if (fileSelectorCombo==null) {
			return null;
		}
		
		int selectionIndex = fileSelectorCombo.getSelectionIndex();
		if (selectionIndex == -1) {
			return null;
		} else if (selectionIndex < userFiles.size()) {
			return userFiles.get(selectionIndex).getFileLocation();
		} else {
			//If it is a new user uploaded file
			boolean copySucceeded = false;
			File destinationFile = new File(user_file_path, new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(new Date()));
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
				userFile.setCreatedByUserId("TODO");
				userFile.setCreatedAt(new Date());
				userFile.setWorkspaceId(projectId);
				userFile.setFileLocation(destinationFile.toPath().toString());
				
				List<UserFileType> userFileTypes = new ArrayList<UserFileType>();
				String[] inputTypes = parameterFile.getInputTypes();
				for (String inputType : inputTypes) {
					UserFileType userFileType = new UserFileType();
					userFileType.setUserFileType(inputType);
					userFileTypes.add(userFileType);
				}
				userFile.setUserFileTypes(userFileTypes);
				
				userFile = UserFileAccess.addUserFileDataToDb(userFile);
				
				return userFile.getFileLocation();
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
