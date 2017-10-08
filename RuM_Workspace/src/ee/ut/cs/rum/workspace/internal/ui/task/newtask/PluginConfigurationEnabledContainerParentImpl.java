package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.util.ArrayList;
import java.util.Collections;

import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationEnabledContainerParent;

public class PluginConfigurationEnabledContainerParentImpl extends PluginConfigurationEnabledContainerParent {
	private static final long serialVersionUID = 8755962797664453874L;
	
	private NewTaskSubTaskInfo newTaskSubTaskInfo;
	private NewTaskDetailsContainer newTaskDetailsContainer;
	
	private ArrayList<SubTask> dependsOnSubTaskIds;

	PluginConfigurationEnabledContainerParentImpl(NewTaskSubTaskInfo newTaskSubTaskInfo) {
		super(newTaskSubTaskInfo);
		this.newTaskSubTaskInfo=newTaskSubTaskInfo;
		this.newTaskDetailsContainer = newTaskSubTaskInfo.getNewTaskDetailsContainer();
		
		this.dependsOnSubTaskIds = new ArrayList<SubTask>();
	}

	@Override
	public UserFile tmpUserFileUploadedNotify(UserFile tmpUserFile) {
		tmpUserFile.setProject(newTaskDetailsContainer.getNewTaskComposite().getProjectTabFolder().getProject());
		newTaskDetailsContainer.notifyTaskOfTmpFileUpload(tmpUserFile);			
		
		return tmpUserFile;		
	}

	@Override
	public void taskUserFileSelectedNotify(UserFile taskUserFile) {
		if (!dependsOnSubTaskIds.contains(taskUserFile.getSubTask())) {
			newTaskDetailsContainer.getNewTaskGeneralInfo().getNewTaskDependenciesScrolledComposite().addDependency(taskUserFile.getSubTask(), newTaskSubTaskInfo.getSubTask());
		}
		dependsOnSubTaskIds.add(taskUserFile.getSubTask());
	}

	@Override
	public void taskUserFileDeselectedNotify(UserFile taskUserFile) {
		if (Collections.frequency(dependsOnSubTaskIds, taskUserFile.getSubTask())==1) {
			newTaskDetailsContainer.getNewTaskGeneralInfo().getNewTaskDependenciesScrolledComposite().removeDependency(taskUserFile.getSubTask(), newTaskSubTaskInfo.getSubTask());			
		}
		dependsOnSubTaskIds.remove(taskUserFile.getSubTask());
	}

}
