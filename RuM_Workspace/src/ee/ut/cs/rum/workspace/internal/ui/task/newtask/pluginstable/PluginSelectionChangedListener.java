package ee.ut.cs.rum.workspace.internal.ui.task.newtask.pluginstable;

import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.custom.ScrolledComposite;
import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.plugins.configuration.util.PluginUtils;
import ee.ut.cs.rum.plugins.development.description.PluginInfo;
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

		newTaskSubTaskInfo.getPluginInfoComposite().updateSelectedPluginInfo(plugin);

		ScrolledComposite scrolledPluginConfigurationComposite = newTaskSubTaskInfo.getScrolledPluginConfigurationComposite();
				
		if (scrolledPluginConfigurationComposite.getContent()!=null) {
			PluginConfigurationComposite prevPluginConfigurationComposite = (PluginConfigurationComposite)scrolledPluginConfigurationComposite.getContent();
			newTaskSubTaskInfo.getNewTaskDetailsContainer().notifyTaskOfPluginDeselect(prevPluginConfigurationComposite.getOutputUserFiles(), newTaskSubTaskInfo);
			
			if (!scrolledPluginConfigurationComposite.getContent().isDisposed()) {
				scrolledPluginConfigurationComposite.getContent().dispose();				
			}
		}

		
		if (plugin !=null) {
			PluginInfo pluginInfo = PluginUtils.deserializePluginInfo(plugin);

			List<UserFile> userFiles = newTaskSubTaskInfo.getNewTaskDetailsContainer().getUserFiles();
			List<UserFile> taskUserFiles = newTaskSubTaskInfo.getNewTaskDetailsContainer().getInitialTaskUserFiles(newTaskSubTaskInfo);
			List<UserFile> tmpUserFiles = newTaskSubTaskInfo.getNewTaskDetailsContainer().getTmpUserFiles();
			
			PluginConfigurationComposite pluginConfigurationComposite = new PluginConfigurationComposite(scrolledPluginConfigurationComposite, pluginInfo, rumController, userFiles, taskUserFiles, tmpUserFiles);
			for (UserFile userFile : pluginConfigurationComposite.getOutputUserFiles()) {
				userFile.setSubTask(newTaskSubTaskInfo.getSubTask());
			}
			
			scrolledPluginConfigurationComposite.setContent(pluginConfigurationComposite);
			pluginConfigurationComposite.setSize(scrolledPluginConfigurationComposite.getSize());
			
			newTaskSubTaskInfo.getNewTaskDetailsContainer().notifyTaskOfPluginSelect(pluginConfigurationComposite.getOutputUserFiles(), newTaskSubTaskInfo);
		} else {
			scrolledPluginConfigurationComposite.setContent(null);
		}
	}
}
