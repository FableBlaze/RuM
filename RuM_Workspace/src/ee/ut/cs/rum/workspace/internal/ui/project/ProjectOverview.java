package ee.ut.cs.rum.workspace.internal.ui.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.workspace.internal.ui.project.taskstable.TasksTableComposite;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskDetails;

public class ProjectOverview extends Composite {
	private static final long serialVersionUID = 7059830950897604661L;
	
	private Project project;
	private ProjectDetailsContainer projectDetailsContainer;

	public ProjectOverview(ProjectTabFolder projectTabFolder, Project project, RumController rumController) {
		super(projectTabFolder, SWT.NONE);
		
		this.project=project;
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		new TasksTableComposite(this, rumController);
		
		projectDetailsContainer = new ProjectDetailsContainer(this, rumController);
		
		Button addTaskDialogueButton = new Button(this, SWT.PUSH);
		addTaskDialogueButton.setText("New task");
		addTaskDialogueButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		((GridData) addTaskDialogueButton.getLayoutData()).horizontalSpan = ((GridLayout) this.getLayout()).numColumns;
		
		addTaskDialogueButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -6086051326689769935L;

			@Override
			public void handleEvent(Event arg0) {
				CTabItem cTabItem = null;

				for (CTabItem c : projectTabFolder.getItems()) {
					if (c.getControl().getClass() == NewTaskDetails.class) {
						cTabItem = c;
						projectTabFolder.setSelection(c);
					}
				}

				if (cTabItem == null) {
					cTabItem = new CTabItem (projectTabFolder, SWT.CLOSE);
					cTabItem.setText ("New task");
					cTabItem.setControl(new NewTaskDetails(projectTabFolder, rumController));
					projectTabFolder.setSelection(cTabItem);
				}
			}
		});
	}
	
	public Project getProject() {
		return project;
	}
	
	public ProjectDetailsContainer getProjectDetailsContainer() {
		return projectDetailsContainer;
	}

}
