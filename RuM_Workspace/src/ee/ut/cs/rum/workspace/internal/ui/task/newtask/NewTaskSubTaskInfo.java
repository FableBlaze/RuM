package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.workspace.internal.Activator;
import ee.ut.cs.rum.workspace.internal.ui.task.PluginInfoComposite;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.pluginstable.PluginsTableComposite;

public class NewTaskSubTaskInfo extends Composite {
	private static final long serialVersionUID = -9081862727975335668L;
	
	private NewTaskDetailsContainer newTaskDetailsContainer;
	
	private SubTask subTask;
	
	private PluginsTableComposite pluginsTableComposite;
	private PluginInfoComposite pluginInfoComposite;
	private ScrolledComposite scrolledPluginConfigurationComposite;

	public NewTaskSubTaskInfo(NewTaskDetailsContainer newTaskDetailsContainer, SubTask subTask, RumController rumController) {
		super(newTaskDetailsContainer, SWT.NONE);
		
		this.newTaskDetailsContainer=newTaskDetailsContainer;
		
		this.subTask = subTask;
		
		this.setLayout(new GridLayout(4, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		pluginsTableComposite = new PluginsTableComposite(this, rumController);
		((GridData) pluginsTableComposite.getLayoutData()).verticalSpan=3;
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Sub-task name:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		Text subTaskNameText = new Text(this, SWT.BORDER);
		subTaskNameText.setText(subTask.getName());
		subTaskNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		((GridData)subTaskNameText.getLayoutData()).widthHint=200;
		subTaskNameText.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 4820831828850851112L;
			@Override
			public void focusLost(FocusEvent event) {
				subTask.setName(subTaskNameText.getText());
				newTaskDetailsContainer.getNewTaskComposite().getDetailsSideBar().getSubTaskTableViewer().refresh(subTask);
				newTaskDetailsContainer.notifyTaskOfSubTaskNameChange(subTask);
			}
			@Override
			public void focusGained(FocusEvent event) {
			}
		});

		scrolledPluginConfigurationComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledPluginConfigurationComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		((GridData) scrolledPluginConfigurationComposite.getLayoutData()).verticalSpan=3;
		
		label = new Label(this, SWT.NONE);
		label.setText("Sub-task description:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));
		Text subTaskDescriptionText = new Text(this, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		subTaskDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		((GridData)subTaskDescriptionText.getLayoutData()).widthHint=200;
		((GridData)subTaskDescriptionText.getLayoutData()).heightHint=75;
		subTaskDescriptionText.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 7788872561606011341L;
			@Override
			public void focusLost(FocusEvent event) {
				subTask.setDescription(subTaskDescriptionText.getText());
			}
			@Override
			public void focusGained(FocusEvent event) {
			}
		});

		pluginInfoComposite = new PluginInfoComposite(this);
		pluginInfoComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		((GridData) pluginInfoComposite.getLayoutData()).horizontalSpan=2;
		((GridData) pluginInfoComposite.getLayoutData()).verticalSpan=3;
	}
	
	public void updateAndCheckSubTask() throws SubTaskUpdateException {
		Table table = pluginsTableComposite.getPluginsTableViewer().getTable();
		
		try {
			if (table.getSelectionIndex()==-1) {
				throw new SubTaskUpdateException("Plugin of subtask " + subTask.getName() + " not selected");
			}
			
			Plugin plugin = (Plugin)table.getItem(table.getSelectionIndex()).getData();							
			PluginConfigurationComposite pluginConfigurationComposite = (PluginConfigurationComposite)scrolledPluginConfigurationComposite.getContent();
			
			if (!pluginConfigurationComposite.getDisplayNamesOfEmptyRequiredParameters().isEmpty()) {
				throw new SubTaskUpdateException("Subtask " + subTask.getName() + " required parameters empty");
			}
			
			Map<String, String> configurationValues = pluginConfigurationComposite.getConfigurationValues();
			Gson gson = new Gson();
			String configurationValuesString = gson.toJson(configurationValues);
			
			subTask.setPlugin(plugin);
			subTask.setConfigurationValues(configurationValuesString);
			subTask.setRequiredDependencies(pluginConfigurationComposite.getDependsOn());
		} catch (SubTaskUpdateException e) {
			throw e;
		} catch (Exception e) {
			Activator.getLogger().info("General issue with subtask " + subTask.getName() + " " + e.toString());
			throw new SubTaskUpdateException("General issue with subtask " + subTask.getName());
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
