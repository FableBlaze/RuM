package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.util.ArrayList;

import ee.ut.cs.rum.database.domain.UserFile;

public class TmpUserFileArrayList extends ArrayList<UserFile> {
	private static final long serialVersionUID = -4636718730639540334L;
	
	private NewTaskDetailsContainer newTaskDetailsContainer;

	public TmpUserFileArrayList(NewTaskDetailsContainer newTaskDetailsContainer) {
		super();
		this.newTaskDetailsContainer=newTaskDetailsContainer;
	}
	
	@Override
	public boolean add(UserFile tmpUserFile) {
		tmpUserFile.setProject(newTaskDetailsContainer.getNewTaskComposite().getProjectTabFolder().getProject());
		boolean result = super.add(tmpUserFile);
		if (result) {
			newTaskDetailsContainer.notifyTaskOfTmpFileUpload(tmpUserFile);			
		}
		return result;
	}

}
