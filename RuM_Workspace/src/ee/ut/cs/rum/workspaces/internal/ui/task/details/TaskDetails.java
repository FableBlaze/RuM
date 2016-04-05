package ee.ut.cs.rum.workspaces.internal.ui.task.details;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.google.gson.Gson;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.util.PluginAccess;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.ui.PluginConfigurationUi;
import ee.ut.cs.rum.workspaces.internal.ui.task.newtask.SelectedPluginInfo;
import ee.ut.cs.rum.workspaces.internal.ui.workspace.WorkspaceTabFolder;
import ee.ut.cs.rum.workspaces.internal.util.PluginUtils;

public class TaskDetails extends Composite {
	private static final long serialVersionUID = 5855252537558430818L;
	
	private Long taskId;
	
	public TaskDetails(WorkspaceTabFolder workspaceTabFolder, Long taskId) {
		super(workspaceTabFolder, SWT.CLOSE);
		
		this.taskId=taskId;
		
		this.setLayout(new GridLayout(2, false));
		
		Task task = TaskAccess.getTaskDataFromDb(taskId);
		
		createContents(task);
	}

	@SuppressWarnings("unchecked")
	private void createContents(Task task) {
		SelectedPluginInfo selectedPluginInfo = new SelectedPluginInfo(this);
		Plugin plugin = PluginAccess.getPluginDataFromDb(task.getPluginId());
		selectedPluginInfo.updateSelectedPluginInfo(plugin);
		
		PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(plugin);
		
		PluginConfigurationUi pluginConfigurationUi = new PluginConfigurationUi(this, pluginInfo);
		pluginConfigurationUi.setEnabled(false);
		
		Gson gson = new Gson();
		Map<String,String> configurationValues = new HashMap<String,String>();
		configurationValues = gson.fromJson(task.getConfigurationValues(), configurationValues.getClass());
		pluginConfigurationUi.setConfigurationValues(configurationValues);
	}
	
	public Long getTaskId() {
		return taskId;
	}
}
