package ee.ut.cs.rum.workspace.internal.ui.task.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskDetails;

//Used for both TaskDetails and NewTaskDetails
public class DetailsSideBar extends Composite {
	private static final long serialVersionUID = 17721630002679216L;

	DetailsSideBar(NewTaskDetails newTaskDetails) {
		super(newTaskDetails, SWT.NONE);
		createContents(newTaskDetails);
	}

	DetailsSideBar(TaskDetails taskDetails) {
		super(taskDetails, SWT.NONE);
		createContents(taskDetails);
	}
	
	private void createContents(Composite parent) {
		// TODO Auto-generated method stub
		
	}

}
