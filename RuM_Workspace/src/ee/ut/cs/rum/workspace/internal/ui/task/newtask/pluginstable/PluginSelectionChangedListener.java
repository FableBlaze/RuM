package ee.ut.cs.rum.workspace.internal.ui.task.newtask.pluginstable;

import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationUi;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationEnabledContainer;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskSubTaskInfo;

public class PluginSelectionChangedListener implements ISelectionChangedListener {

	private RumController rumController;
	
	private NewTaskSubTaskInfo newTaskSubTaskInfo;

	public PluginSelectionChangedListener(NewTaskSubTaskInfo newTaskSubTaskInfo, RumController rumController) {
		this.rumController=rumController;
		
		this.newTaskSubTaskInfo=newTaskSubTaskInfo;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		Plugin plugin = null;

		if (event!=null) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			plugin = (Plugin) selection.getFirstElement();			
		}
		
		PluginConfigurationEnabledContainer pluginConfigurationEnabledContainer = newTaskSubTaskInfo.getPluginConfigurationEnabledContainer(); 
		PluginConfigurationUi pluginConfigurationUi = pluginConfigurationEnabledContainer.getPluginConfigurationUi();
		if (pluginConfigurationUi!=null) {
			newTaskSubTaskInfo.getNewTaskDetailsContainer().notifyTaskOfPluginDeselect(pluginConfigurationUi.getOutputUserFiles(), newTaskSubTaskInfo);
		}
		
		if (plugin!=null) {
			List<UserFile> userFiles = newTaskSubTaskInfo.getNewTaskDetailsContainer().getUserFiles();
			List<UserFile> taskUserFiles = newTaskSubTaskInfo.getNewTaskDetailsContainer().getInitialTaskUserFiles(newTaskSubTaskInfo);
			List<UserFile> tmpUserFiles = newTaskSubTaskInfo.getNewTaskDetailsContainer().getTmpUserFiles();
			
			pluginConfigurationEnabledContainer.showEnabledPluginConfigurationUi(plugin, rumController, userFiles, taskUserFiles, tmpUserFiles);
			List<UserFile> newOutputUserFiles = pluginConfigurationEnabledContainer.getPluginConfigurationUi().getOutputUserFiles();
			newOutputUserFiles.forEach(outputFile -> outputFile.setSubTask(newTaskSubTaskInfo.getSubTask()));
			newTaskSubTaskInfo.getNewTaskDetailsContainer().notifyTaskOfPluginSelect(newOutputUserFiles, newTaskSubTaskInfo);
		} else {
			pluginConfigurationEnabledContainer.disposeCurrentPluginConfigurationUi();
		}
		
		newTaskSubTaskInfo.getPluginInfoComposite().updateSelectedPluginInfo(plugin);
	}
}
