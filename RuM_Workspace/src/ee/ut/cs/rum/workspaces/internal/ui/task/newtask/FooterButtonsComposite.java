package ee.ut.cs.rum.workspaces.internal.ui.task.newtask;

import java.util.Date;
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
import com.google.gson.Gson;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.TaskStatusEnum;
import ee.ut.cs.rum.plugins.development.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.scheduler.util.TaskScheduling;
import ee.ut.cs.rum.workspaces.internal.ui.task.details.TaskDetails;
import ee.ut.cs.rum.workspaces.internal.util.TasksData;

public class FooterButtonsComposite extends Composite {
	private static final long serialVersionUID = 688156596045927568L;

	private NewTaskDetails newTaskDetails;

	public FooterButtonsComposite(Composite scrolledfooterButtonsComposite, NewTaskDetails newTaskDetails) {
		super(scrolledfooterButtonsComposite, SWT.NONE);

		this.newTaskDetails=newTaskDetails;
		this.setLayout(new GridLayout(3, false));

		Button button;

		button = new Button(this, SWT.PUSH);
		button.setText("Start and show tasks");
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, true));
		button.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 5694975289507094763L;

			public void widgetSelected(SelectionEvent event) {
				createNewTask();
				newTaskDetails.getWorkspaceTabFolder().getSelection().dispose();
				newTaskDetails.getWorkspaceTabFolder().setSelection(0);
			}
		});

		button = new Button(this, SWT.PUSH);
		button.setText("Start and add next");
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true));
		button.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 5694975289507094763L;

			public void widgetSelected(SelectionEvent event) {
				createNewTask();
				newTaskDetails.getPluginsTableComposite().getPluginsTableViewer().getTable().deselectAll();
				newTaskDetails.getPluginsTableComposite().getPluginSelectionChangedListener().selectionChanged(null);
			}
		});

		button = new Button(this, SWT.PUSH);
		button.setText("Start and show details");
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true));
		button.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 5694975289507094763L;

			public void widgetSelected(SelectionEvent event) {
				Task task = createNewTask();
				newTaskDetails.getWorkspaceTabFolder().getSelection().dispose();
				
				CTabItem cTabItem = new CTabItem (newTaskDetails.getWorkspaceTabFolder(), SWT.CLOSE);
				cTabItem.setText ("Task " + task.getId().toString());
				cTabItem.setControl(new TaskDetails(newTaskDetails.getWorkspaceTabFolder(), task.getId()));
				newTaskDetails.getWorkspaceTabFolder().setSelection(cTabItem);
			}
		});
		
		this.setEnabled(false);
	}

	private Task createNewTask(){
		IStructuredSelection selection = (IStructuredSelection) newTaskDetails.getPluginsTableComposite().getPluginsTableViewer().getSelection();

		PluginConfigurationComposite pluginConfigurationUi = (PluginConfigurationComposite)newTaskDetails.getScrolledPluginConfigurationComposite().getContent();
		Map<String, String> configurationValues = pluginConfigurationUi.getConfigurationValues();
		Gson gson = new Gson();
		String configurationValuesString = gson.toJson(configurationValues);

		Plugin selectedPlugin = (Plugin) selection.getFirstElement();
		Task task = new Task();
		task.setName("TODO");
		task.setStatus(TaskStatusEnum.NEW);
		task.setPluginId(selectedPlugin.getId());
		task.setDescription("TODO");
		task.setConfigurationValues(configurationValuesString);
		task.setCreatedBy("TODO");
		task.setCreatedAt(new Date());
		task.setWorkspaceId(newTaskDetails.getWorkspaceTabFolder().getWorkspace().getId());
		
		task = TasksData.addTaskDataToDb(task, newTaskDetails);
		TaskScheduling.scheduleTask(task.getId());
		
		return task;
	}
}
