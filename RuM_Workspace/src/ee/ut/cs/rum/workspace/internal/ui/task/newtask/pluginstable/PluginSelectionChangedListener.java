package ee.ut.cs.rum.workspace.internal.ui.task.newtask.pluginstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskSubTaskInfo;

public class PluginSelectionChangedListener implements ISelectionChangedListener {
	
	private NewTaskSubTaskInfo newTaskSubTaskInfo;
	
	public PluginSelectionChangedListener(NewTaskSubTaskInfo newTaskSubTaskInfo) {
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
	}
}
