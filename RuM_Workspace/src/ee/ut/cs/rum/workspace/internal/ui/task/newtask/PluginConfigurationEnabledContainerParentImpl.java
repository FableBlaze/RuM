package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationEnabledContainerParent;

public class PluginConfigurationEnabledContainerParentImpl extends PluginConfigurationEnabledContainerParent {
	private static final long serialVersionUID = 8755962797664453874L;
	
	private NewTaskSubTaskInfo newTaskSubTaskInfo;
	private NewTaskDetailsContainer newTaskDetailsContainer;

	PluginConfigurationEnabledContainerParentImpl(NewTaskSubTaskInfo newTaskSubTaskInfo) {
		super(newTaskSubTaskInfo);
		this.newTaskSubTaskInfo=newTaskSubTaskInfo;
		this.newTaskDetailsContainer = newTaskSubTaskInfo.getNewTaskDetailsContainer();
	}

	@Override
	public UserFile tmpUserFileUploadedNotify(UserFile tmpUserFile) {
		tmpUserFile.setProject(newTaskDetailsContainer.getNewTaskComposite().getProjectTabFolder().getProject());
		newTaskDetailsContainer.notifyTaskOfTmpFileUpload(tmpUserFile);			
		
		return tmpUserFile;		
	}

	@Override
	public void taskUserFileSelectedNotify(UserFile taskUserFile) {
		newTaskDetailsContainer.getNewTaskGeneralInfo().getNewTaskDependenciesScrolledComposite().addDependency(taskUserFile.getSubTask(), newTaskSubTaskInfo.getSubTask());
	}

	@Override
	public void taskUserFileDeselectedNotify(UserFile taskUserFile) {
		newTaskDetailsContainer.getNewTaskGeneralInfo().getNewTaskDependenciesScrolledComposite().removeDependency(taskUserFile.getSubTask(), newTaskSubTaskInfo.getSubTask());
	}

}
