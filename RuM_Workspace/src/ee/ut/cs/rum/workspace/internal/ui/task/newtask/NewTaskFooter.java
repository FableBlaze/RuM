package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.enums.SystemParameterName;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.util.SystemParameterAccess;
import ee.ut.cs.rum.database.util.exceptions.SystemParameterNotSetException;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.scheduler.util.RumScheduler;
import ee.ut.cs.rum.workspace.internal.Activator;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.sidebar.SubTaskTableViewer;

public class NewTaskFooter extends Composite {
	private static final long serialVersionUID = -8265567504413682063L;

	private int subTaskNameCounter;
	private Button startTaskButton;
	private Button removeSubTaskButton;

	public NewTaskFooter(NewTaskComposite newTaskComposite, RumController rumController) {
		super(newTaskComposite, SWT.NONE);

		subTaskNameCounter=1;

		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		startTaskButton = new Button(this, SWT.PUSH);
		startTaskButton.setText("Start task");
		startTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		startTaskButton.setEnabled(false);
		startTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -2573553526165067810L;

			@Override
			public void handleEvent(Event event) {
				try {
					SystemParameterAccess.getSystemParameterValue(SystemParameterName.TASK_RESULTS_ROOT);
					Task task = newTaskComposite.getTask();
					List<SubTask> subTasks = new ArrayList<SubTask>();
					boolean subTasksOk = true;

					List<NewTaskSubTaskInfo> newTaskSubTaskInfoList = newTaskComposite.getNewTaskDetailsContainer().getNewTaskSubTaskInfoList();
					for (NewTaskSubTaskInfo newTaskSubTaskInfo : newTaskSubTaskInfoList) {
						Date createdAt = new Date();
						String userName = "TODO";

						if (newTaskSubTaskInfo.updateAndCheckSubTask()) {
							SubTask subTask = newTaskSubTaskInfo.getSubTask();
							subTask.setCreatedBy(userName);
							subTask.setCreatedAt(createdAt);
							subTask.setLastModifiedBy(userName);
							subTask.setLastModifiedAt(createdAt);
							subTasks.add(subTask);
						} else {
							subTasksOk=false;
							break;
						}
					}

					task.setSubTasks(subTasks);

					if (subTasksOk && !subTasks.isEmpty()) {
						task = (Task)rumController.changeData(ControllerUpdateType.CREATE, ControllerEntityType.TASK, task, "TODO");

						RumScheduler.scheduleTask(task.getId());
						Activator.getLogger().info("Queued task: " + task.toString());						
					} else {
						task.getSubTasks().clear();
						Activator.getLogger().info("Error queing task: " + task.toString());
					}
				} catch (SystemParameterNotSetException e) {
					Activator.getLogger().info("Can not queue task " + e.toString());
				}
			}
		});

		Button addSubTaskButton = new Button(this, SWT.PUSH);
		addSubTaskButton.setText("Add sub-task");
		addSubTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		addSubTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 5479827915459796014L;

			@Override
			public void handleEvent(Event event) {
				SubTaskTableViewer subTaskTableViewer = newTaskComposite.getDetailsSideBar().getSubTaskTableViewer();
				SubTask subTask = new SubTask();
				subTask.setName("(Sub-task " + subTaskNameCounter++ + ")");
				subTask.setDescription("");
				subTask.setStatus(TaskStatus.NEW);
				subTaskTableViewer.add(subTask);
				subTaskTableViewer.getTable().select(subTaskTableViewer.getTable().getItemCount()-1);

				NewTaskDetailsContainer newTaskDetailsContainer = newTaskComposite.getNewTaskDetailsContainer();
				NewTaskSubTaskInfo newTaskSubTaskInfo = new NewTaskSubTaskInfo(newTaskDetailsContainer, subTask, rumController);
				newTaskDetailsContainer.getNewTaskSubTaskInfoList().add(newTaskSubTaskInfo);				
				newTaskDetailsContainer.showSubTaskInfo(subTaskTableViewer.getTable().getItemCount()-1);

				if (!startTaskButton.getEnabled()) {
					startTaskButton.setEnabled(true);
				}
			}
		});

		removeSubTaskButton = new Button(this, SWT.PUSH);
		removeSubTaskButton.setText("Remove sub-task");
		removeSubTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		removeSubTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1736754942801222766L;

			@Override
			public void handleEvent(Event event) {
				Table table = newTaskComposite.getDetailsSideBar().getSubTaskTableViewer().getTable();
				NewTaskDetailsContainer newTaskDetailsContainer = newTaskComposite.getNewTaskDetailsContainer();
				PluginConfigurationComposite pluginConfigurationComposite = (PluginConfigurationComposite)newTaskDetailsContainer.getNewTaskSubTaskInfoList().get(table.getSelectionIndex()).getScrolledPluginConfigurationComposite().getContent();

				if (pluginConfigurationComposite!=null) {
					newTaskDetailsContainer.notifyTaskOfPluginDeselect(pluginConfigurationComposite.getOutputUserFiles(), newTaskDetailsContainer.getNewTaskSubTaskInfoList().get(table.getSelectionIndex()));
				}

				newTaskDetailsContainer.getNewTaskSubTaskInfoList().remove(table.getSelectionIndex());
				table.remove(table.getSelectionIndex());
				newTaskDetailsContainer.showGeneralInfo();

				if (table.getItemCount()==0 && startTaskButton.getEnabled()) {
					startTaskButton.setEnabled(false);
				}
			}
		});
		removeSubTaskButton.setVisible(false);
	}

	public void setRemoveSubTaskButtonVisible(boolean visible) {
		removeSubTaskButton.setVisible(visible);
	}
}
