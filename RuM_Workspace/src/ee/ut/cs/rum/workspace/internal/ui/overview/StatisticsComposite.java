package ee.ut.cs.rum.workspace.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.enums.ControllerListenerType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.workspace.internal.Activator;

public class StatisticsComposite extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = -6946774249575610410L;
	
	private Display display;
	private RumController rumController;
	
	private Label totalProjects;
	private Label totalTasks;

	StatisticsComposite(WorkspaceOverviewExpandBar workspaceOverviewExpandBar, RumController rumController) {
		super(workspaceOverviewExpandBar, SWT.NONE);
		
		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerListenerType.TASK);
		
		GridLayout layout = new GridLayout(2, false);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		layout.verticalSpacing = 10;
		this.setLayout(layout);
		
		Label label = new Label (this, SWT.NONE);
		label.setText("Number of projects:");
		totalProjects = new Label (this, SWT.NONE);
		totalProjects.setText("TODO");
		label = new Label (this, SWT.NONE);
		label.setText("Number of tasks:");
		totalTasks = new Label (this, SWT.NONE);
		totalTasks.setText("TODO");
		
		Button addProjectDialogueButton = new Button(this, SWT.PUSH);
		addProjectDialogueButton.setText("Create a new project");
		addProjectDialogueButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false));

		addProjectDialogueButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 5383804225331390829L;

			public void handleEvent(Event arg0) {
				rumController.changeDtata();
			}
		});
	}
	
	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		try {
			Activator.getLogger().info("Sleeping!");
			Thread.sleep(5000L);
			Activator.getLogger().info("Sleep done!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (updatedEntity instanceof Task) {
			display.asyncExec(new Runnable() {
				public void run() {
					totalProjects.setText(totalProjects.getText()+"test");
					totalTasks.setText(totalTasks.getText()+"test");
					totalProjects.getParent().layout();
				}
			});
		}
		
	}
	
	@Override
	public void dispose() {
		rumController.unregisterView(this, ControllerListenerType.TASK);
		super.dispose();
	}

}
