package ee.ut.cs.rum.workspace.internal.ui.project.taskstable;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;
import ee.ut.cs.rum.workspace.internal.ui.task.details.TaskDetailsComposite;

public class TaskRowDoubleClickListener implements IDoubleClickListener {
	
	private ProjectTabFolder projectTabFolder;
	private RumController rumController;
	
	public TaskRowDoubleClickListener(ProjectTabFolder projectTabFolder, RumController rumController) {
		this.projectTabFolder=projectTabFolder;
		this.rumController=rumController;
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		Task selectedTask = null;
		CTabItem cTabItem = null;
		
		if (event!=null) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			selectedTask = (Task) selection.getFirstElement();
			Long taskId = selectedTask.getId();
			
			//Checking if the tab is already open
			for (CTabItem c : projectTabFolder.getItems()) {
				if (c.getControl().getClass() == TaskDetailsComposite.class) {
					if (((TaskDetailsComposite)c.getControl()).getTask().getId() == taskId) {
						cTabItem = c;
						projectTabFolder.setSelection(c);
					}
				}
			}

			if (cTabItem == null) {
				cTabItem = new CTabItem (projectTabFolder, SWT.CLOSE);
				cTabItem.setText ("Task " + taskId.toString());
				cTabItem.setControl(new TaskDetailsComposite(projectTabFolder, rumController, taskId));
				projectTabFolder.setSelection(cTabItem);	
			}
		}
		
		
		
	}

}
