package ee.ut.cs.rum.plugins.configuration.internal.ui;

import java.io.File;

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

import ee.ut.cs.rum.plugins.configuration.internal.Activator;
import ee.ut.cs.rum.plugins.development.description.parameter.PluginParameterFile;

public class ConfigurationItemFile extends Composite implements ConfigurationItemInterface {
	private static final long serialVersionUID = 3599873879215927039L;
	
	//Temporary file is always last on fileSelectorCombo
	private File temporaryFile;

	public ConfigurationItemFile(Composite parent, PluginParameterFile parameterFile) {
		super(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		this.setLayout(gridLayout);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setToolTipText(parameterFile.getDescription());
		
		createContents();
	}
	
	private void createContents() {
		Combo fileSelectorCombo = new Combo(this, SWT.READ_ONLY);
		fileSelectorCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
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
		
	}

	@Override
	public String getValue() {
		//TODO: Check if selected UserFile object has id (if not - create and save to database) then return id
		
		
		return "a";
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}
}
