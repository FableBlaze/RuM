package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.google.gson.Gson;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.enums.SystemParameterName;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.util.SystemParameterAccess;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.scheduler.util.TaskScheduling;
import ee.ut.cs.rum.workspace.internal.Activator;
import ee.ut.cs.rum.workspace.internal.ui.task.details.TaskDetails;

public class FooterButtonsComposite extends Composite {
	private static final long serialVersionUID = 688156596045927568L;

	private NewTaskDetails newTaskDetails;
	private File task_results_root;

	public FooterButtonsComposite(Composite scrolledfooterButtonsComposite, NewTaskDetails newTaskDetails) {
		super(scrolledfooterButtonsComposite, SWT.NONE);
		
		String task_results_root_asString = SystemParameterAccess.getSystemParameterValue(SystemParameterName.TASK_RESULTS_ROOT);
		if (task_results_root_asString!=null) {
			task_results_root = new File(task_results_root_asString);
		}

		this.newTaskDetails=newTaskDetails;
		this.setLayout(new GridLayout(3, false));
		
		if (task_results_root==null) {
			Label label = new Label(this, SWT.NONE);
			label.setText("Adding tasks disabled!");
		} else {
			createContents();
			this.setEnabled(false);
		}
	}
	
	private void createContents() {
		Button button;
		
		button = new Button(this, SWT.PUSH);
		button.setText("Start and show tasks");
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, true));
		button.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 5694975289507094763L;
			
			public void widgetSelected(SelectionEvent event) {
				Task task = createNewTask();
				if (task!=null) {
					newTaskDetails.getWorkspaceTabFolder().getSelection().dispose();
					newTaskDetails.getWorkspaceTabFolder().setSelection(0);					
				}
			}
		});
		
		button = new Button(this, SWT.PUSH);
		button.setText("Start and add next");
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true));
		button.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 5694975289507094763L;
			
			public void widgetSelected(SelectionEvent event) {
				Task task = createNewTask();
				if (task!=null) {
					newTaskDetails.getPluginsTableComposite().getPluginsTableViewer().getTable().deselectAll();
					newTaskDetails.getPluginsTableComposite().getPluginSelectionChangedListener().selectionChanged(null);
				}
			}
		});
		
		button = new Button(this, SWT.PUSH);
		button.setText("Start and show details");
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true));
		button.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 5694975289507094763L;
			
			public void widgetSelected(SelectionEvent event) {
				Task task = createNewTask();
				if (task!=null) {
					newTaskDetails.getWorkspaceTabFolder().getSelection().dispose();
					
					CTabItem cTabItem = new CTabItem (newTaskDetails.getWorkspaceTabFolder(), SWT.CLOSE);
					cTabItem.setText ("Task " + task.getId().toString());
					cTabItem.setControl(new TaskDetails(newTaskDetails.getWorkspaceTabFolder(), task.getId()));
					newTaskDetails.getWorkspaceTabFolder().setSelection(cTabItem);
					
					//TODO: Properly updating the UI (MCV)
					List<Task> workspaceTasks = TaskAccess.getWorkspaceTasksDataFromDb(newTaskDetails.getWorkspaceTabFolder().getWorkspace().getId());
					newTaskDetails.getWorkspaceTabFolder().getWorkspaceDetailsTabContents().getTasksTableViewer().setInput(workspaceTasks);
				}
			}
		});
		
	}

	//TODO: Feedback to user if task creation fails
	private Task createNewTask(){
		Task task = null;
		
		IStructuredSelection selection = (IStructuredSelection) newTaskDetails.getPluginsTableComposite().getPluginsTableViewer().getSelection();

		PluginConfigurationComposite pluginConfigurationUi = (PluginConfigurationComposite)newTaskDetails.getScrolledPluginConfigurationComposite().getContent();
		Map<String, String> configurationValues = pluginConfigurationUi.getConfigurationValues();
		Gson gson = new Gson();
		String configurationValuesString = gson.toJson(configurationValues);

		Date createdAt = new Date();
		File taskResultsPath = new File(task_results_root, new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(createdAt));
		
		if (taskResultsPath.mkdir()) {
			Plugin selectedPlugin = (Plugin) selection.getFirstElement();
			task = new Task();
			task.setName("TODO");
			task.setStatus(TaskStatus.NEW);
			task.setPluginId(selectedPlugin.getId());
			task.setDescription("TODO");
			task.setConfigurationValues(configurationValuesString);
			task.setCreatedBy("TODO");
			task.setCreatedAt(createdAt);
			task.setProjectId(newTaskDetails.getWorkspaceTabFolder().getWorkspace().getId());
			task.setOutputPath(taskResultsPath.getPath());
			
			task = TaskAccess.addTaskDataToDb(task);
			TaskScheduling.scheduleTask(task.getId());			
		} else {
			Activator.getLogger().info("Failed creating task output folder: " + taskResultsPath.getPath());
		}
		
		return task;
	}
}
