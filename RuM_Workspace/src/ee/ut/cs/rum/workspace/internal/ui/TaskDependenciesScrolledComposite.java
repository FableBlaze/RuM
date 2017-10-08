package ee.ut.cs.rum.workspace.internal.ui;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.SubTaskDependency;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.workspace.internal.ui.project.details.ProjectTaskDetails;
import ee.ut.cs.rum.workspace.internal.ui.task.details.TaskGeneralInfo;

public class TaskDependenciesScrolledComposite extends ScrolledComposite {
	private static final long serialVersionUID = -1808002202497735357L;
	
	private Composite dependsOnContents;
	private Composite subTaskNamesComposite;
	private Composite requiredForContents;

	public TaskDependenciesScrolledComposite(ProjectTaskDetails projectTaskDetails, Task task) {
		super(projectTaskDetails, SWT.V_SCROLL);
		createContents();
		addTaskInfo(task);
	}

	public TaskDependenciesScrolledComposite(TaskGeneralInfo taskGeneralInfo, Task task) {
		super(taskGeneralInfo, SWT.V_SCROLL);
		createContents();
		addTaskInfo(task);
	}

	private void createContents() {
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite taskDependenciesContents = new Composite(this, SWT.NONE);
		taskDependenciesContents.setLayout(new GridLayout(3, false));
		taskDependenciesContents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		ScrolledComposite dependsOnScrolledComposite = new ScrolledComposite(taskDependenciesContents, SWT.H_SCROLL);
		dependsOnScrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		dependsOnContents = new Composite(dependsOnScrolledComposite, SWT.NONE);
		dependsOnContents.setLayout(new GridLayout());
		dependsOnContents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		dependsOnScrolledComposite.setContent(dependsOnContents);


		subTaskNamesComposite = new Composite(taskDependenciesContents, SWT.NONE);
		subTaskNamesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		subTaskNamesComposite.setLayout(new GridLayout());


		ScrolledComposite requiredForScrolledComposite = new ScrolledComposite(taskDependenciesContents, SWT.H_SCROLL);
		requiredForScrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		requiredForContents = new Composite(requiredForScrolledComposite, SWT.NONE);
		requiredForContents.setLayout(new GridLayout());
		requiredForContents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		requiredForScrolledComposite.setContent(requiredForContents);

		this.setContent(taskDependenciesContents);
		this.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = -8072559449087915608L;

			@Override
			public void handleEvent(Event event) {
				int x1 = TaskDependenciesScrolledComposite.this.getSize().x-5;
				taskDependenciesContents.setSize(taskDependenciesContents.computeSize(x1, SWT.DEFAULT));

				int x2 = ((TaskDependenciesScrolledComposite.this.getSize().x-5)-subTaskNamesComposite.getSize().x)/2;
				if (dependsOnContents.computeSize(SWT.DEFAULT, SWT.DEFAULT).x > x2) {
					dependsOnContents.setSize(dependsOnContents.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				} else {
					dependsOnContents.setSize(dependsOnContents.computeSize(dependsOnScrolledComposite.getBounds().width-5, SWT.DEFAULT));
				}

				if (requiredForContents.computeSize(SWT.DEFAULT, SWT.DEFAULT).x > x2) {
					requiredForContents.setSize(requiredForContents.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				} else {
					requiredForContents.setSize(requiredForContents.computeSize(requiredForScrolledComposite.getBounds().width-5, SWT.DEFAULT));
				}
			}
		});
	}
	
	private void addTaskInfo(Task task) {
		Label l;
		List<SubTask> subTasks= task.getSubTasks();
		//subTasks.sort(Comparator.comparing(SubTask::getId));
		for (SubTask subTask : subTasks) {
			
			l = new Label(dependsOnContents,SWT.NONE);
			GridData gd = new GridData(SWT.RIGHT, SWT.FILL, true, false);
			gd.heightHint=20;
			l.setLayoutData(gd);
			List<SubTask> fulfilledBySubTaskSet = new ArrayList<SubTask>();
			List<SubTaskDependency> requiredSubTaskDependencies = subTask.getRequiredDependencies();
			//requiredSubTaskDependencies.sort((o1, o2) -> o1.getFulfilledBySubTask().getId().compareTo(o2.getFulfilledBySubTask().getId()));
			for (SubTaskDependency subTaskDependency : requiredSubTaskDependencies) {
				if (!fulfilledBySubTaskSet.contains(subTaskDependency.getFulfilledBySubTask())) {
					fulfilledBySubTaskSet.add(subTaskDependency.getFulfilledBySubTask());
				}
			}
			if (!fulfilledBySubTaskSet.isEmpty()) {
				String fulfilledBySubTasString = "";
				for (SubTask fulfilledBySubTask : fulfilledBySubTaskSet) {
					fulfilledBySubTasString+=fulfilledBySubTask.getName() + "; ";
				}
				l.setText(fulfilledBySubTasString.substring(0, fulfilledBySubTasString.length()-2) + "  >>");
			}
			
			l = new Label(subTaskNamesComposite,SWT.NONE);
			l.setText(subTask.getName());
			gd = new GridData(SWT.CENTER, SWT.FILL, false, false);
			gd.heightHint=20;
			l.setLayoutData(gd);
			
			l = new Label(requiredForContents,SWT.NONE);
			gd = new GridData();
			gd.heightHint=20;
			l.setLayoutData(gd);
			List<SubTask> requiredForSubTaskSet = new ArrayList<SubTask>();
			List<SubTaskDependency> fulfilledSubTaskDependencies = subTask.getFulfilledDependencies();
			//requiredSubTaskDependencies.sort((o1, o2) -> o1.getRequiredBySubTask().getId().compareTo(o2.getRequiredBySubTask().getId()));
			for (SubTaskDependency subTaskDependency : fulfilledSubTaskDependencies) {
				if (!requiredForSubTaskSet.contains(subTaskDependency.getRequiredBySubTask())) {
					requiredForSubTaskSet.add(subTaskDependency.getRequiredBySubTask());
				}
			}
			if (!requiredForSubTaskSet.isEmpty()) {
				String requiredForSubTasString = "";
				for (SubTask requiredForSubTask : requiredForSubTaskSet) {
					requiredForSubTasString+=requiredForSubTask.getName() + "; ";
				}
				l.setText(">>  " + requiredForSubTasString.substring(0, requiredForSubTasString.length()-2));
			}
		}	
	}
}
