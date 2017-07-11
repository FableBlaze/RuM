package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.google.gson.Gson;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.workspace.internal.Activator;
import ee.ut.cs.rum.workspace.internal.ui.task.PluginInfoComposite;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.pluginstable.PluginsTableComposite;

public class NewTaskSubTaskInfo extends Composite {
	private static final long serialVersionUID = -9081862727975335668L;
	
	private NewTaskDetailsContainer newTaskDetailsContainer;
	
	private SubTask subTask;
	
	private Text subTaskNameText;
	private Text subTaskDescriptionText;
	
	private PluginsTableComposite pluginsTableComposite;
	private PluginInfoComposite pluginInfoComposite;
	private ScrolledComposite scrolledPluginConfigurationComposite;

	public NewTaskSubTaskInfo(NewTaskDetailsContainer newTaskDetailsContainer, RumController rumController) {
		super(newTaskDetailsContainer, SWT.NONE);
		
		this.newTaskDetailsContainer=newTaskDetailsContainer;
		
		this.subTask = new SubTask();
		subTask.setStatus(TaskStatus.NEW);
		
		this.setLayout(new GridLayout(4, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		pluginsTableComposite = new PluginsTableComposite(this, rumController);
		((GridData) pluginsTableComposite.getLayoutData()).verticalSpan=3;
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Sub-task name:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		subTaskNameText = new Text(this, SWT.BORDER);
		subTaskNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		((GridData)subTaskNameText.getLayoutData()).widthHint=200;
		subTaskNameText.addModifyListener(new ModifyListener() {
			private static final long serialVersionUID = -4275986926694404712L;

			@Override
			public void modifyText(ModifyEvent event) {
				Table table = newTaskDetailsContainer.getNewTaskComposite().getDetailsSideBar().getSubTaskTableViewer().getTable();
				table.getItem(table.getSelectionIndex()).setText(0, subTaskNameText.getText());
			}
		});

		scrolledPluginConfigurationComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledPluginConfigurationComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//((GridData) scrolledPluginConfigurationComposite.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns-2;
		((GridData) scrolledPluginConfigurationComposite.getLayoutData()).verticalSpan=3;
		
		label = new Label(this, SWT.NONE);
		label.setText("Sub-task description:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));
		subTaskDescriptionText = new Text(this, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		subTaskDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		((GridData)subTaskDescriptionText.getLayoutData()).widthHint=200;
		((GridData)subTaskDescriptionText.getLayoutData()).heightHint=75;

		pluginInfoComposite = new PluginInfoComposite(this);
		pluginInfoComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		((GridData) pluginInfoComposite.getLayoutData()).horizontalSpan=2;
		((GridData) pluginInfoComposite.getLayoutData()).verticalSpan=3;
	}
	
	public boolean updateAndCheckSubTask() {
		subTask.setName(subTaskNameText.getText());
		subTask.setDescription(subTaskDescriptionText.getText());
		
		Table table = pluginsTableComposite.getPluginsTableViewer().getTable();
		try {
			//TODO: More intelligent error handling
			Plugin plugin = (Plugin)table.getItem(table.getSelectionIndex()).getData();							
			subTask.setPlugin(plugin);
			PluginConfigurationComposite pluginConfigurationComposite = (PluginConfigurationComposite)scrolledPluginConfigurationComposite.getContent();
			Map<String, String> configurationValues = pluginConfigurationComposite.getConfigurationValues();
			Gson gson = new Gson();
			String configurationValuesString = gson.toJson(configurationValues);
			subTask.setConfigurationValues(configurationValuesString);
			
			List<Map<String, String>> dependsOn = pluginConfigurationComposite.getDependsOn();
			String dependsOnString = gson.toJson(dependsOn);
			//subTask.setDependsOn(dependsOnString);
			
			return true;
		} catch (Exception e) {
			Activator.getLogger().info(e.toString());
			return false;
		}
	}
	
	public SubTask getSubTask() {
		return subTask;
	}
	
	public NewTaskDetailsContainer getNewTaskDetailsContainer() {
		return newTaskDetailsContainer;
	}
	
	public PluginInfoComposite getPluginInfoComposite() {
		return pluginInfoComposite;
	}
	
	public ScrolledComposite getScrolledPluginConfigurationComposite() {
		return scrolledPluginConfigurationComposite;
	}
}
