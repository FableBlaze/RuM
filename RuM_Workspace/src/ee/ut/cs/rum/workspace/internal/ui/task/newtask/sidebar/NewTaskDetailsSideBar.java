package ee.ut.cs.rum.workspace.internal.ui.task.newtask.sidebar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.enums.SubTaskStatus;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskComposite;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskDetailsContainer;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskSubTaskInfo;

public class NewTaskDetailsSideBar extends Composite {
	private static final long serialVersionUID = 17721630002679216L;
	
	private RumController rumController;
	
	private NewTaskComposite newTaskComposite;
	private SubTaskTableViewer subTaskTableViewer;

	public NewTaskDetailsSideBar(NewTaskComposite newTaskComposite, RumController rumController) {
		super(newTaskComposite, SWT.NONE);
		
		this.rumController=rumController;
		
		this.newTaskComposite = newTaskComposite;
		
		this.setLayout(new GridLayout(1, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Sub-tasks:");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		
		Button generalInfoButton = new Button(this, SWT.PUSH);
		generalInfoButton.setText("General info");
		generalInfoButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		generalInfoButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -9188529455494099314L;

			@Override
			public void handleEvent(Event arg0) {
				newTaskComposite.getNewTaskDetailsContainer().showGeneralInfo();
				subTaskTableViewer.getTable().deselectAll();
			}
		});
		
		this.subTaskTableViewer = new SubTaskTableViewer(this);
		subTaskTableViewer.addSelectionChangedListener(new SubTaskSelectionChangedListener(newTaskComposite));
	}
	
	public void initializeBasedOnTask(Task baseTask) {
		for (SubTask baseSubTask : baseTask.getSubTasks()) {
			SubTask subTask = new SubTask();
			subTask.setName(baseSubTask.getName());
			subTask.setDescription(baseSubTask.getDescription());
			subTask.setStatus(SubTaskStatus.NEW);
			subTaskTableViewer.add(subTask);
			
			NewTaskDetailsContainer newTaskDetailsContainer = newTaskComposite.getNewTaskDetailsContainer();
			NewTaskSubTaskInfo newTaskSubTaskInfo = new NewTaskSubTaskInfo(newTaskDetailsContainer, subTask, rumController);
			newTaskDetailsContainer.getNewTaskSubTaskInfoList().add(newTaskSubTaskInfo);
			newTaskDetailsContainer.getNewTaskGeneralInfo().getNewTaskDependenciesScrolledComposite().addSubTask(subTask);
			newTaskSubTaskInfo.initializeBasedOnSubTask(baseSubTask);
		}
	}
	
	public SubTaskTableViewer getSubTaskTableViewer() {
		return subTaskTableViewer;
	}
}
