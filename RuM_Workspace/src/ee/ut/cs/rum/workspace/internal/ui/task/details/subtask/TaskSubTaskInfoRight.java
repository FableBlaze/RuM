package ee.ut.cs.rum.workspace.internal.ui.task.details.subtask;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.google.gson.Gson;

import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationContainer;

public class TaskSubTaskInfoRight extends Composite {
	private static final long serialVersionUID = 2108550824294466913L;

	@SuppressWarnings("unchecked")
	public TaskSubTaskInfoRight(TaskSubTaskInfo taskSubTaskInfo, SubTask subTask) {
		super(taskSubTaskInfo, SWT.NONE);
		
		this.setLayout(new GridLayout(1, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		PluginConfigurationContainer pluginConfigurationContainer = new PluginConfigurationContainer(this);
		pluginConfigurationContainer.showDisabledPluginConfigurationUi(subTask.getPlugin());
		
		Gson gson = new Gson();
		Map<String,String> configurationValues = new HashMap<String,String>();
		configurationValues = gson.fromJson(subTask.getConfigurationValues(), configurationValues.getClass());
		pluginConfigurationContainer.getPluginConfigurationUi().setConfigurationValues(configurationValues);		
	}

}
