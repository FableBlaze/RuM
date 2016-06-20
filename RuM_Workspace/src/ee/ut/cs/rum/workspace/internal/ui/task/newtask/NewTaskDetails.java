package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;

public class NewTaskDetails extends Composite {
	private static final long serialVersionUID = -4167600812621979994L;

	NewTaskDetails(ProjectTabFolder projectTabFolder) {
		super(projectTabFolder, SWT.NONE);
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

}
