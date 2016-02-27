package ee.ut.cs.rum.plugins.internal.ui;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.Activator;
import ee.ut.cs.rum.plugins.internal.util.PluginsData;

public class PluginUploadDialog extends Dialog {
	
	public PluginUploadDialog(Shell parent) {
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}
	public PluginUploadDialog(Shell parent, int style) {
		super(parent, style);
	}
	
	public String open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		createContents(shell);
		shell.pack();
		shell.setLocation (100, 100);
		shell.open();
		return null;
	}

	private void createContents(final Shell shell) {
		shell.setLayout(new GridLayout(2, true));
		
		final Text pluginName = new Text(shell, SWT.BORDER);
		pluginName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Text pluginDescription = new Text(shell, SWT.BORDER);
		pluginDescription.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();
		final FileUploadHandler uploadHandler = new FileUploadHandler( receiver );
		uploadHandler.addUploadListener( new FileUploadListener() {
			public void uploadProgress( FileUploadEvent event ) {}
			public void uploadFailed( FileUploadEvent event ) {}
			public void uploadFinished( FileUploadEvent event ) {
				Activator.getLogger().info( "Stored file: " + receiver.getTargetFiles()[ 0 ].getAbsolutePath() );
			}
		} );

		final FileUpload fileUpload = new FileUpload( shell, SWT.NONE );
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		fileUpload.setLayoutData(gridData);
		fileUpload.setText( "Select File" );
		fileUpload.addSelectionListener( new SelectionAdapter() {
			@Override
			public void widgetSelected( SelectionEvent e ) {
				fileUpload.submit( uploadHandler.getUploadUrl() );
			}
		} );
		

		// Create the OK button and add a handler
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(gridData);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Plugin plugin = new Plugin();
				plugin.setName(pluginName.getText());
				plugin.setDescription(pluginDescription.getText());
				PluginsData.addPluginDataToDb(plugin);
				shell.close();
			}
		});

		// Create the cancel button and add a handler
		Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		cancel.setLayoutData(gridData);
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});

		// Set the OK button as the default, so
		// user can type input and press Enter
		// to dismiss
		shell.setDefaultButton(ok);
	}
}

