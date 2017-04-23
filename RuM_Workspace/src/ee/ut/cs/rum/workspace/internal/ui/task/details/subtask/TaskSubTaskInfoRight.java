package ee.ut.cs.rum.workspace.internal.ui.task.details.subtask;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.google.gson.Gson;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.plugins.configuration.util.PluginUtils;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;

public class TaskSubTaskInfoRight extends Composite {
	private static final long serialVersionUID = 2108550824294466913L;

	@SuppressWarnings("unchecked")
	public TaskSubTaskInfoRight(TaskSubTaskInfo taskSubTaskInfo, RumController rumController, SubTask subTask) {
		super(taskSubTaskInfo, SWT.NONE);
		
		this.setLayout(new GridLayout(1, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		ScrolledComposite scrolledPluginConfigurationComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledPluginConfigurationComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(subTask.getPlugin());
		PluginConfigurationComposite pluginConfigurationComposite = new PluginConfigurationComposite(scrolledPluginConfigurationComposite, pluginInfo, rumController, null, null);
		Gson gson = new Gson();
		Map<String,String> configurationValues = new HashMap<String,String>();
		configurationValues = gson.fromJson(subTask.getConfigurationValues(), configurationValues.getClass());
		pluginConfigurationComposite.setConfigurationValues(configurationValues);
		
		scrolledPluginConfigurationComposite.setContent(pluginConfigurationComposite);
		pluginConfigurationComposite.setSize(pluginConfigurationComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

}
