package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.sidebar.DetailsSideBar;

public class NewTask extends Composite {
	private static final long serialVersionUID = -4167600812621979994L;

	public NewTask(ProjectTabFolder projectTabFolder, RumController rumController) {
		super(projectTabFolder, SWT.NONE);
		
		//TODO: Listen to plugin changes
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		new DetailsSideBar(this);
		
		//TODO: Content composite
		
		NewTaskFooter newTaskFooter = new NewTaskFooter(this);
		((GridData) newTaskFooter.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
	}

}
