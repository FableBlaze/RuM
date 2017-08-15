package ee.ut.cs.rum.plugins.configuration.internal.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.domain.UserFileType;
import ee.ut.cs.rum.database.domain.enums.SystemParameterName;
import ee.ut.cs.rum.database.util.SystemParameterAccess;
import ee.ut.cs.rum.database.util.UserFileAccess;
import ee.ut.cs.rum.database.util.exceptions.SystemParameterNotSetException;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.plugins.configuration.internal.Activator;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;

public class ConfigurationItemFile extends Composite implements ConfigurationItemInterface {
	private static final long serialVersionUID = 3599873879215927039L;

	private String internalName;
	private String displayName;
	private boolean required;
	
	private RumController rumController;

	private List<UserFile> userFilesInSelector;
	private List<UserFile> taskUserFilesInSelector;
	private List<UserFile> tmpUserFilesInSelector;

	private Combo fileSelectorCombo;
	private FileUploadHandler uploadHandler;
	private File user_file_path;
	private PluginParameterFile parameterFile;
	private PluginConfigurationComposite pluginConfigurationComposite;
	
	private int preEventSelectionIndex;

	public ConfigurationItemFile(PluginConfigurationComposite pluginConfigurationComposite, PluginParameterFile parameterFile, RumController rumController) {
		super(pluginConfigurationComposite, SWT.NONE);
		
		this.internalName=parameterFile.getInternalName();
		this.displayName=parameterFile.getDisplayName();
		this.setToolTipText(parameterFile.getDescription());
		this.required=parameterFile.getRequired();

		this.rumController=rumController;
		
		this.preEventSelectionIndex=-1;

		this.pluginConfigurationComposite = pluginConfigurationComposite;
		this.parameterFile = parameterFile;
		String user_file_path_asString;
		try {
			user_file_path_asString = SystemParameterAccess.getSystemParameterValue(SystemParameterName.USER_FILE_PATH);
			user_file_path = new File(user_file_path_asString);
		} catch (SystemParameterNotSetException e) {
			Activator.getLogger().info("File upload disabled " + e.toString());
		}

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		createContents();
		
		createUploadHandler();
	}

	private void createContents() {
		fileSelectorCombo = new Combo(this, SWT.READ_ONLY);
		fileSelectorCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		fileSelectorCombo.addSelectionListener(new SelectionListener() {			
			private static final long serialVersionUID = -2671867325224354752L;
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (fileSelectorCombo.getSelectionIndex()==preEventSelectionIndex && event.stateMask==SWT.CTRL) {
					fileSelectorCombo.deselectAll();
				}
				preEventSelectionIndex = fileSelectorCombo.getSelectionIndex();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});

		List<UserFile> userFiles = pluginConfigurationComposite.getUserFiles();
		this.userFilesInSelector = new ArrayList<UserFile>();
		if (userFiles!=null) {
			for (UserFile userFile : userFiles) {
				if (checkFileTypes(userFile)) {
					fileSelectorCombo.add(userFile.getOriginalFilename() + " (" + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(userFile.getCreatedAt()) + ")");
					userFilesInSelector.add(userFile);
				}
			}
		}
		
		List<UserFile> taskUserFiles = pluginConfigurationComposite.getTaskUserFiles();
		this.taskUserFilesInSelector = new ArrayList<UserFile>();
		if (taskUserFiles!=null) {
			for (UserFile taskUserFile : taskUserFiles) {
				if (checkFileTypes(taskUserFile)) {
					fileSelectorCombo.add(taskUserFile.getOriginalFilename() + " (" + taskUserFile.getSubTask().getName() + ")");
					taskUserFilesInSelector.add(taskUserFile);
				}
			}
		}

		List<UserFile> tmpUserFiles = pluginConfigurationComposite.getTmpUserFiles();
		this.tmpUserFilesInSelector = new ArrayList<UserFile>();
		if (tmpUserFiles!=null) {
			for (UserFile tmpUserFile : tmpUserFiles) {
				if (checkFileTypes(tmpUserFile)) {					
					fileSelectorCombo.add(tmpUserFile.getOriginalFilename());
					tmpUserFilesInSelector.add(tmpUserFile);
				}
			}
		}

		if (user_file_path==null && userFiles!=null) {
			Label label = new Label(this, SWT.NONE);
			label.setText("File upload disabled!");
		} else {			
			FileUpload fileUpload = new FileUpload(this, SWT.NONE);
			fileUpload.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
			fileUpload.setText("Upload");
			fileUpload.addSelectionListener(new SelectionAdapter() {
				private static final long serialVersionUID = -7623994796399336054L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					fileSelectorCombo.deselectAll();
					fileUpload.submit(uploadHandler.getUploadUrl());
				}
			});
		}
	}

	private void createUploadHandler() {
		this.uploadHandler = new FileUploadHandler(new DiskFileUploadReceiver());
		uploadHandler.addUploadListener(new FileUploadListener() {
			@Override
			public void uploadProgress(FileUploadEvent arg0) {
			}
			@Override
			public void uploadFailed(FileUploadEvent arg0) {
			}

			@Override
			public void uploadFinished(FileUploadEvent arg0) {
				DiskFileUploadReceiver receiver = (DiskFileUploadReceiver) uploadHandler.getReceiver();
				File temporaryFile = receiver.getTargetFiles()[receiver.getTargetFiles().length-1];
				Activator.getLogger().info("Uploaded file: " + temporaryFile.getAbsolutePath());

				UserFile tmpUserFile = new UserFile();
				tmpUserFile.setOriginalFilename(temporaryFile.getName());
				tmpUserFile.setFileLocation(temporaryFile.getAbsolutePath());
				List<UserFileType> userFileTypes = new ArrayList<UserFileType>();
				String[] inputTypes = parameterFile.getInputTypes();
				for (String inputType : inputTypes) {
					UserFileType userFileType = new UserFileType();
					userFileType.setTypeName(inputType);
					userFileTypes.add(userFileType);
				}
				tmpUserFile.setUserFileTypes(userFileTypes);

				UserFile finalTmpUserFile = pluginConfigurationComposite.addTmpUserFile(tmpUserFile);
				tmpUserFilesInSelector.add(finalTmpUserFile);

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						fileSelectorCombo.select(fileSelectorCombo.getItemCount()-1);
					}
				});
			}
		});
	}

	@Override
	public void setValue(String fileLocation) {
		//TODO: Setting the value when required subtask has failed
		if (fileLocation!=null && !fileLocation.equals("")) {
			if (pluginConfigurationComposite.isEnabled()==false) {
				UserFile userFile = UserFileAccess.getUserFileDataFromDb(fileLocation);
				if (userFile!=null) {
					fileSelectorCombo.add(userFile.getOriginalFilename() + " (" + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(userFile.getCreatedAt()) + ")");
					fileSelectorCombo.select(0);	
				}
			} else {
				for (int i = 0; i < userFilesInSelector.size(); i++) {
					if (userFilesInSelector.get(i).getFileLocation().equals(fileLocation)) {
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
		} else if (selectionIndex < userFilesInSelector.size()) {
			return userFilesInSelector.get(selectionIndex).getFileLocation();
		} else if (selectionIndex < userFilesInSelector.size()+taskUserFilesInSelector.size()) {
			return "";
		} else {
			UserFile tmpUserFile = tmpUserFilesInSelector.get(selectionIndex-userFilesInSelector.size()-taskUserFilesInSelector.size());
			
			if (tmpUserFile.getCreatedAt()==null) {
				//If it is a new user uploaded file
				boolean copySucceeded = false;
				File destinationFile = new File(user_file_path, new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(new Date()));
				try {
					Files.copy( Paths.get(tmpUserFile.getFileLocation()), destinationFile.toPath());
					copySucceeded = true;
					Activator.getLogger().info("Copied uploaded file to: " + destinationFile.toPath());
				} catch (IOException e) {
					Activator.getLogger().info("Failed to copy uploaded file to: " + destinationFile.toPath());
				}
				
				if (copySucceeded) {
					tmpUserFile.setFileLocation(destinationFile.toPath().toString());
					tmpUserFile = (UserFile)this.rumController.changeData(ControllerUpdateType.CREATE, ControllerEntityType.USER_FILE, tmpUserFile, "TODO");
					
					return tmpUserFile.getFileLocation();
				} else {
					return null;
				}				
			} else {
				return tmpUserFile.getFileLocation();
			}
		}
	}
	
	public UserFile getDependsOnFile() {
		int selectionIndex = fileSelectorCombo.getSelectionIndex();
		
		if (selectionIndex >= userFilesInSelector.size() && selectionIndex < userFilesInSelector.size()+taskUserFilesInSelector.size()) {
			return taskUserFilesInSelector.get(selectionIndex - userFilesInSelector.size());
		} else {
			return null;			
		}
	}

	private boolean checkFileTypes(UserFile userFile) {
		for (UserFileType userFileType : userFile.getUserFileTypes()) {
			for (String inputType : parameterFile.getInputTypes()) {
				if (userFileType.getTypeName().equals(inputType)) {
					return true;
				}
			}
		}
		return false;
	}

	
	public void addUserFile(UserFile userFile) {
		if (checkFileTypes(userFile)) {
			fileSelectorCombo.add(userFile.getOriginalFilename() + " (" + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(userFile.getCreatedAt()) + ")", userFilesInSelector.size());
			if (fileSelectorCombo.getSelectionIndex()>=userFilesInSelector.size()) {
				fileSelectorCombo.select(fileSelectorCombo.getSelectionIndex()+1);
			}
			userFilesInSelector.add(userFile);
		}
	}

	public void modifyUserFile(UserFile userFile) {
		if (checkFileTypes(userFile)) {
			int i = userFilesInSelector.indexOf(userFile);
			userFilesInSelector.set(i, userFile);
			fileSelectorCombo.remove(i);
			fileSelectorCombo.add(userFile.getOriginalFilename() + " (" + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(userFile.getCreatedAt()) + ")", i);
		}
	}

	public void removeUserFile(UserFile userFile) {
		if (checkFileTypes(userFile)) {
			int i = userFilesInSelector.indexOf(userFile);
			if (fileSelectorCombo.getSelectionIndex()==-1) {
				userFilesInSelector.remove(i);
				fileSelectorCombo.remove(i);
			} else {
				userFilesInSelector.remove(i);
				fileSelectorCombo.remove(i);
				fileSelectorCombo.select(i);
			}
		}
	}

	public void notifyFileParameterOfPluginSelect(List<UserFile> outputFiles) {
		for (UserFile userFile : outputFiles) {
			if (checkFileTypes(userFile)) {
				fileSelectorCombo.add(userFile.getOriginalFilename() + " (" + userFile.getSubTask().getName() + ")", userFilesInSelector.size()+taskUserFilesInSelector.size());
				if (fileSelectorCombo.getSelectionIndex()>=userFilesInSelector.size()+taskUserFilesInSelector.size()) {
					fileSelectorCombo.select(fileSelectorCombo.getSelectionIndex()+1);
				}
				taskUserFilesInSelector.add(userFile);
			}
		}
	}

	public void notifyFileParameterOfPluginDeselect(List<UserFile> outputFiles) {
		for (UserFile userFile : outputFiles) {
			if (checkFileTypes(userFile)) {
				int i = taskUserFilesInSelector.indexOf(userFile);
				taskUserFilesInSelector.remove(i);
				fileSelectorCombo.remove(i + userFilesInSelector.size());
			}
		}
	}
	
	public void notifyFileParameterOfSubTaskNameChange(List<UserFile> outputFiles) {
		for (UserFile userFile : outputFiles) {
			if (checkFileTypes(userFile)) {
				int selectionIndex = fileSelectorCombo.getSelectionIndex();
				int i = taskUserFilesInSelector.indexOf(userFile);
				fileSelectorCombo.remove(i + userFilesInSelector.size());
				fileSelectorCombo.add(userFile.getOriginalFilename() + " (" + userFile.getSubTask().getName() + ")", i + userFilesInSelector.size());
				fileSelectorCombo.select(selectionIndex);
			}
		}
	}

	public void notifyFileParameterOfTmpFileUpload(UserFile tmpUserFile) {
		if (checkFileTypes(tmpUserFile) && !tmpUserFilesInSelector.contains(tmpUserFile)) {
			tmpUserFilesInSelector.add(tmpUserFile);
			fileSelectorCombo.add(tmpUserFile.getOriginalFilename());
		}
	}

	@Override
	public String getInternalName() {
		return internalName;
	}
	
	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public boolean getRequired() {
		return required;
	}
}
