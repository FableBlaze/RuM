package ee.ut.cs.rum.files.internal.ui.details.left;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.domain.UserFileType;

public class UserFileGeneralDetails extends Composite {

	private static final long serialVersionUID = 6962780544823550119L;

	public UserFileGeneralDetails(UserFileDetailsLeft userFileDetailsLeft) {
		super (userFileDetailsLeft, SWT.NONE);

		UserFile userFile = userFileDetailsLeft.getUserFileDetails().getUserFile();

		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label l = new Label(this, SWT.NONE);
		l.setText("Id:");

		l = new Label(this, SWT.NONE);
		l.setText(userFile.getId().toString());

		l = new Label(this, SWT.NONE);
		l.setText("Created at:");

		l = new Label(this, SWT.NONE);
		l.setText(new SimpleDateFormat("dd-MM-yyyy").format(userFile.getCreatedAt()));

		l = new Label(this, SWT.NONE);
		l.setText("Created by:");

		l = new Label(this, SWT.NONE);
		l.setText(userFile.getCreatedBy());

		l = new Label(this, SWT.NONE);
		l.setText("Last modified at:");

		l = new Label(this, SWT.NONE);
		l.setText(new SimpleDateFormat("dd-MM-yyyy").format(userFile.getLastModifiedAt()));

		l = new Label(this, SWT.NONE);
		l.setText("Last modified by:");

		l = new Label(this, SWT.NONE);
		l.setText(userFile.getLastModifiedBy());

		l = new Label(this, SWT.NONE);
		l.setText("Original filename:");

		l = new Label(this, SWT.NONE);
		l.setText(userFile.getOriginalFilename());

		l = new Label(this, SWT.NONE);
		l.setText("Created by plugin:");

		l = new Label(this, SWT.NONE);
		if (userFile.getPlugin() != null) {
			l.setText(userFile.getPlugin().getPluginName());
			l.setToolTipText("Id: " + userFile.getPlugin().getId().toString());
		}

		l = new Label(this, SWT.NONE);
		l.setText("Created in project:");

		l = new Label(this, SWT.NONE);
		if (userFile.getProject() != null) {
			l.setText(userFile.getProject().getName());
			l.setToolTipText("Id: " + userFile.getProject().getId().toString());
		}

		l = new Label(this, SWT.NONE);
		l.setText("Created in task:");

		l = new Label(this, SWT.NONE);
		if (userFile.getTask() != null) {
			l.setText(userFile.getTask().getName());
			l.setToolTipText("Id: " + userFile.getTask().getId().toString());
		}

		l = new Label(this, SWT.NONE);
		l.setText("Created by sub-task:");

		l = new Label(this, SWT.NONE);
		if (userFile.getSubTask() != null) {
			l.setText(userFile.getSubTask().getName());
			l.setToolTipText("Id: " + userFile.getSubTask().getId().toString());
		}

		l = new Label(this, SWT.NONE);
		l.setText("File Types:");

		Composite fileTypesContainer = new Composite(this, SWT.NONE);
		fileTypesContainer.setLayout(new FillLayout(SWT.VERTICAL));
		for (UserFileType userFileType : userFile.getUserFileTypes()) {
			l = new Label (fileTypesContainer, SWT.NONE);
			l.setText(userFileType.getTypeName());
		}

	}

}
