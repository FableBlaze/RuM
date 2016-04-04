package ee.ut.cs.rum.workspaces.internal.ui.task.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.util.PluginAccess;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
import ee.ut.cs.rum.plugins.development.description.deserializer.PluginInfoDeserializer;
import ee.ut.cs.rum.plugins.development.ui.PluginConfigurationUi;
import ee.ut.cs.rum.workspaces.internal.ui.task.newtask.SelectedPluginInfo;
import ee.ut.cs.rum.workspaces.internal.ui.workspace.WorkspaceTabFolder;

public class TaskDetails extends ScrolledComposite {
	private static final long serialVersionUID = 5855252537558430818L;
	
	private Long taskId;
	private Composite content;

	public TaskDetails(WorkspaceTabFolder workspaceTabFolder, Long taskId) {
		super(workspaceTabFolder, SWT.CLOSE | SWT.H_SCROLL | SWT.V_SCROLL);
		
		this.content = new Composite(this, SWT.NONE);
		content.setLayout(new GridLayout(2, false));
		this.setContent(content);
		
		Task task = TaskAccess.getTaskDataFromDb(taskId);
		
		createContents(task);
		
		content.setSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private void createContents(Task task) {
		SelectedPluginInfo selectedPluginInfo = new SelectedPluginInfo(content);
		Plugin plugin = PluginAccess.getPluginDataFromDb(task.getPluginId());
		selectedPluginInfo.updateSelectedPluginInfo(plugin);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(PluginInfo.class, new PluginInfoDeserializer());
		Gson gson = gsonBuilder.create();
		PluginInfo pluginInfo = gson.fromJson(plugin.getPluginInfo(), PluginInfo.class);
		
		PluginConfigurationUi pluginConfigurationUi = new PluginConfigurationUi(content, pluginInfo);
		pluginConfigurationUi.setEnabled(false);
	}
	
	public Long getTaskId() {
		return taskId;
	}
}
