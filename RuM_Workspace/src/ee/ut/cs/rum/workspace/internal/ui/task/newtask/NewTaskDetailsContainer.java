package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;

public class NewTaskDetailsContainer extends Composite {
	private static final long serialVersionUID = -7982581022298012511L;
	
	private NewTaskComposite newTaskComposite;
	private NewTaskGeneralInfo newTaskGeneralInfo;

	public NewTaskDetailsContainer(NewTaskComposite newTaskComposite, RumController rumController) {
		super(newTaskComposite, SWT.NONE);
		
		this.newTaskComposite=newTaskComposite;
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new StackLayout());
		
		newTaskGeneralInfo = new NewTaskGeneralInfo(this);
		
		((StackLayout)this.getLayout()).topControl = newTaskGeneralInfo;
		this.layout();
	}
	
	public void showGeneralInfo() {
		((StackLayout)this.getLayout()).topControl = newTaskGeneralInfo;
		this.layout();
		newTaskComposite.getNewTaskFooter().setRemoveSubTaskButtonVisible(false);
	}
	
	public void showSubTaskInfo() {
		
		newTaskComposite.getNewTaskFooter().setRemoveSubTaskButtonVisible(true);
	}
}
