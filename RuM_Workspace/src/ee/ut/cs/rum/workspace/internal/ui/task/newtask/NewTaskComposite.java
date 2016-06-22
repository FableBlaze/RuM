package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.sidebar.DetailsSideBar;

public class NewTaskComposite extends Composite {
	private static final long serialVersionUID = -4167600812621979994L;

	private ProjectTabFolder projectTabFolder;
	
	private DetailsSideBar detailsSideBar;
	private NewTaskDetailsContainer newTaskDetailsContainer;
	private NewTaskFooter newTaskFooter;

	public NewTaskComposite(ProjectTabFolder projectTabFolder, RumController rumController) {
		super(projectTabFolder, SWT.NONE);

		//TODO: Listen to plugin changes
		
		this.projectTabFolder=projectTabFolder;

		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		detailsSideBar = new DetailsSideBar(this);

		newTaskDetailsContainer = new NewTaskDetailsContainer(this, rumController);

		newTaskFooter = new NewTaskFooter(this);
		((GridData) newTaskFooter.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
	}

	public ProjectTabFolder getProjectTabFolder() {
		return projectTabFolder;
	}
	
	public DetailsSideBar getDetailsSideBar() {
		return detailsSideBar;
	}

	public NewTaskDetailsContainer getNewTaskDetailsContainer() {
		return newTaskDetailsContainer;
	}
	
	public NewTaskFooter getNewTaskFooter() {
		return newTaskFooter;
	}
}
