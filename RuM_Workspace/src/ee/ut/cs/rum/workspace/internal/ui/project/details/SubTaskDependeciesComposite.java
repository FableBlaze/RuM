package ee.ut.cs.rum.workspace.internal.ui.project.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class SubTaskDependeciesComposite extends Composite {
	private static final long serialVersionUID = -1013654238898171258L;

	SubTaskDependeciesComposite(ProjectTaskDetails projectTaskDetails) {
		super(projectTaskDetails, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3, false));
	}

}
