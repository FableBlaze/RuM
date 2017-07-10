package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;

import com.google.gson.Gson;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.enums.SystemParameterName;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.util.SystemParameterAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.scheduler.util.RumScheduler;
import ee.ut.cs.rum.workspace.internal.Activator;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.sidebar.SubTaskTableViewer;

public class NewTaskFooter extends Composite {
	private static final long serialVersionUID = -8265567504413682063L;

	private int subTaskNameCounter;
	private Button removeSubTaskButton;

	public NewTaskFooter(NewTaskComposite newTaskComposite, RumController rumController) {
		super(newTaskComposite, SWT.NONE);

		subTaskNameCounter=1;

		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		//TODO: Button should be disabled when all required data is not entered
		Button startTaskButton = new Button(this, SWT.PUSH);
		startTaskButton.setText("Start task");
		startTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		startTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -2573553526165067810L;

			@Override
			public void handleEvent(Event event) {

				String task_results_root_asString = SystemParameterAccess.getSystemParameterValue(SystemParameterName.TASK_RESULTS_ROOT);
				if (task_results_root_asString!=null) {

					Task task = new Task();
					NewTaskGeneralInfo newTaskGeneralInfo = newTaskComposite.getNewTaskDetailsContainer().getNewTaskGeneralInfo();
					task.setName(newTaskGeneralInfo.getNewTaskName());
					task.setDescription(newTaskGeneralInfo.getNewTaskDescription());
					task.setStatus(TaskStatus.NEW);
					task.setProject(newTaskComposite.getProjectTabFolder().getProject());

					List<SubTask> subTasks = new ArrayList<SubTask>();

					boolean taskInfoOk =true;
					
					List<NewTaskSubTaskInfo> newTaskSubTaskInfoList = newTaskComposite.getNewTaskDetailsContainer().getNewTaskSubTaskInfoList();
					for (NewTaskSubTaskInfo newTaskSubTaskInfo : newTaskSubTaskInfoList) {
						Date createdAt = new Date();

						SubTask subTask = new SubTask();
						subTask.setName(newTaskSubTaskInfo.getSubTaskName());
						subTask.setDescription(newTaskSubTaskInfo.getSubTaskDescription());
						subTask.setStatus(TaskStatus.NEW);

						Table table = newTaskSubTaskInfo.getPluginsTableComposite().getPluginsTableViewer().getTable();
						try {
							//TODO: More intelligent error handling
							Plugin plugin = (Plugin)table.getItem(table.getSelectionIndex()).getData();							
							subTask.setPlugin(plugin);
							PluginConfigurationComposite pluginConfigurationComposite = (PluginConfigurationComposite)newTaskSubTaskInfo.getScrolledPluginConfigurationComposite().getContent();
							Map<String, String> configurationValues = pluginConfigurationComposite.getConfigurationValues();
							Gson gson = new Gson();
							String configurationValuesString = gson.toJson(configurationValues);
							subTask.setConfigurationValues(configurationValuesString);
							
							List<Map<String, String>> dependsOn = pluginConfigurationComposite.getDependsOn();
							String dependsOnString = gson.toJson(dependsOn);
							subTask.setDependsOn(dependsOnString);
							
						} catch (Exception e) {
							taskInfoOk=false;
							Activator.getLogger().info(e.toString());
						}

						subTask.setCreatedBy("TODO");
						subTask.setCreatedAt(createdAt);
						subTask.setLastModifiedBy("TODO");
						subTask.setLastModifiedAt(createdAt);

						subTasks.add(subTask);
					}
					
					if (taskInfoOk && !subTasks.isEmpty()) {
						task.setSubTasks(subTasks);
						task = (Task)rumController.changeData(ControllerUpdateType.CREATE, ControllerEntityType.TASK, task, "TODO");
						
						RumScheduler.scheduleTask(task.getId());
						Activator.getLogger().info("Queued task: " + task.toString());						
					} else {
						Activator.getLogger().info("Error queing task: " + task.toString());
					}
					
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
				subTaskTableViewer.add(subTask);
				subTaskTableViewer.getTable().select(subTaskTableViewer.getTable().getItemCount()-1);
				newTaskComposite.getNewTaskDetailsContainer().showSubTaskInfo(subTaskTableViewer.getTable().getItemCount()-1);
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
				
				newTaskDetailsContainer.notifyTaskOfPluginDeselect(pluginConfigurationComposite.getOutputUserFiles());
				newTaskDetailsContainer.getNewTaskSubTaskInfoList().remove(table.getSelectionIndex());
				table.remove(table.getSelectionIndex());
				newTaskDetailsContainer.showGeneralInfo();
			}
		});
		removeSubTaskButton.setVisible(false);
	}

	public void setRemoveSubTaskButtonVisible(boolean visible) {
		removeSubTaskButton.setVisible(visible);
	}
}
