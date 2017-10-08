package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.workspace.internal.Activator;

public class NewTaskDependenciesScrolledComposite extends ScrolledComposite {
	private static final long serialVersionUID = -7929702586611542936L;
	
	private Composite dependsOnContents;
	private Composite subTaskNamesComposite;
	private Composite requiredForContents;

	public NewTaskDependenciesScrolledComposite(NewTaskGeneralInfo newTaskGeneralInfo) {
		super(newTaskGeneralInfo, SWT.V_SCROLL);
		createContents();
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
				int x1 = NewTaskDependenciesScrolledComposite.this.getSize().x-5;
				taskDependenciesContents.setSize(taskDependenciesContents.computeSize(x1, SWT.DEFAULT));

				int x2 = ((NewTaskDependenciesScrolledComposite.this.getSize().x-5)-subTaskNamesComposite.getSize().x)/2;
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
	
	public void initializeBasedOnTask(Task baseTask) {
		Activator.getLogger().info("initializing NewTaskDependenciesScrolledComposite based on task: " + baseTask);
	}

	public void clearContents() {
		Activator.getLogger().info("clearing NewTaskDependenciesScrolledComposite");
	}

	public void addSubTask(SubTask subTask) {
		Activator.getLogger().info("adding NewTaskDependenciesScrolledComposite subtask: " + subTask);
	}

	public void removeSubtask(SubTask subTask) {
		Activator.getLogger().info("removing NewTaskDependenciesScrolledComposite subtask: " + subTask);
	}

	public void changeSubTaskName(SubTask subTask) {
		Activator.getLogger().info("Changing NewTaskDependenciesScrolledComposite subtask name: " + subTask);
	}

	public void addDependency(SubTask dependsOnSubTask, SubTask requiredForSubTask) {
		Activator.getLogger().info("Adding dependency: " + dependsOnSubTask.getName() + " >> " + requiredForSubTask.getName());
	}
	
	public void removeDependency(SubTask dependsOnSubTask, SubTask requiredForSubTask) {
		Activator.getLogger().info("Removing dependency: " + dependsOnSubTask.getName() + " >> " + requiredForSubTask.getName());
	}
}
