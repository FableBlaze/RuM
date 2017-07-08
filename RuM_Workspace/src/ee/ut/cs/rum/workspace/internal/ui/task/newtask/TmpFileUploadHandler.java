package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.io.File;

import org.eclipse.rap.fileupload.DiskFileUploadReceiver;
import org.eclipse.rap.fileupload.FileUploadEvent;
import org.eclipse.rap.fileupload.FileUploadHandler;
import org.eclipse.rap.fileupload.FileUploadListener;

public class TmpFileUploadHandler extends FileUploadHandler {

	public TmpFileUploadHandler(NewTaskDetailsContainer newTaskDetailsContainer) {
		super(new DiskFileUploadReceiver());
		
		DiskFileUploadReceiver receiver = (DiskFileUploadReceiver) this.getReceiver();
		
		this.addUploadListener(new FileUploadListener() {
			@Override
			public void uploadProgress(FileUploadEvent arg0) {
			}
			@Override
			public void uploadFailed(FileUploadEvent arg0) {
			}

			@Override
			public void uploadFinished(FileUploadEvent arg0) {
				File temporaryFile = receiver.getTargetFiles()[receiver.getTargetFiles().length-1];
				newTaskDetailsContainer.notifyTaskOfTmpFileUpload(temporaryFile.getAbsolutePath());
			}

		});
	}

}
