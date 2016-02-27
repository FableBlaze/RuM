package ee.ut.cs.rum.plugins.internal.ui.dialog;

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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.Activator;
import ee.ut.cs.rum.plugins.internal.ui.OverviewTabContents;
import ee.ut.cs.rum.plugins.internal.util.PluginsData;

public class PluginUploadDialog extends Dialog {
	private static final long serialVersionUID = 3382119816602279394L;
	
	private OverviewTabContents overviewTabContents;
	
	private Label fileName;
	private Button ok;
	
	public PluginUploadDialog(Shell activeShell, OverviewTabContents overviewTabContents) {
		super(activeShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.overviewTabContents = overviewTabContents;
	}

	public String open() {
		Shell shell = new Shell(getParent(), getStyle());
		createContents(shell);
		shell.pack();
		shell.setLocation (100, 100);
		shell.open();
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
				Activator.getLogger().info("Stored file: " + receiver.getTargetFiles()[0].getAbsolutePath());
				//TODO: This takes too long, check if can be done in the UI thread directly
				Display.getDefault().syncExec(new Runnable() {
				    public void run() {
				    	if (!fileName.isDisposed()) {
				    		fileName.setText(receiver.getTargetFiles()[0].getName());
				    		ok.setEnabled(true);
				    	}
				    }
				});
			}
		} );

		FileUpload fileUpload = new FileUpload( shell, SWT.NONE );
		GridData gridData = new GridData();
		gridData.horizontalSpan = 1;
		fileUpload.setLayoutData(gridData);
		fileUpload.setText( "Select File" );
		fileUpload.addSelectionListener( new SelectionAdapter() {
			private static final long serialVersionUID = -5887356014040291468L;

			@Override
			public void widgetSelected( SelectionEvent e ) {
				fileUpload.submit(uploadHandler.getUploadUrl());
				fileName.setText("");
				ok.setEnabled(false);
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
				shell.close();
			}
		});
	}
}

