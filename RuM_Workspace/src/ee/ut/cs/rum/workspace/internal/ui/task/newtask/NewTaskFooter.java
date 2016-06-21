package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.workspace.internal.Activator;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.sidebar.SubTaskTableViewer;

public class NewTaskFooter extends Composite {
	private static final long serialVersionUID = -8265567504413682063L;
	
	private Button removeSubTaskButton;
	
	public NewTaskFooter(NewTaskComposite newTaskComposite) {
		super(newTaskComposite, SWT.NONE);
		
		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		Button startTaskButton = new Button(this, SWT.PUSH);
		startTaskButton.setText("Start task");
		startTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		startTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -2573553526165067810L;

			@Override
			public void handleEvent(Event event) {
				Task task = new Task();
				NewTaskGeneralInfo newTaskGeneralInfo = newTaskComposite.getNewTaskDetailsContainer().getNewTaskGeneralInfo();
				task.setName(newTaskGeneralInfo.getNewTaskName());
				task.setDescription(newTaskGeneralInfo.getNewTaskDescription());
				
				TableItem[] tableItems = newTaskComposite.getDetailsSideBar().getSubTaskTableViewer().getTable().getItems();
				for (TableItem tableItem : tableItems) {
					SubTask subTask = (SubTask)tableItem.getData();
					subTask.setTask(task);
					Activator.getLogger().info(subTask.toString());
				}
				Activator.getLogger().info(task.toString());
			}
		});
		
		Button addSubTaskButton = new Button(this, SWT.PUSH);
		addSubTaskButton.setText("Add sub-task");
		addSubTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		addSubTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 5479827915459796014L;

			@Override
			public void handleEvent(Event event) {
				SubTaskTableViewer subTaskTableViewer = newTaskComposite.getDetailsSideBar().getSubTaskTableViewer();
				SubTask subTask = new SubTask();
				subTask.setName("Sub-task " + (subTaskTableViewer.getTable().getItemCount()+1));
				subTaskTableViewer.add(subTask);
				subTaskTableViewer.getTable().select(subTaskTableViewer.getTable().getItemCount()-1);
				newTaskComposite.getNewTaskDetailsContainer().showSubTaskInfo(subTask);
				//TODO: Switch to sub-task
			}
		});
		
		removeSubTaskButton = new Button(this, SWT.PUSH);
		removeSubTaskButton.setText("Remove sub-task");
		removeSubTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		removeSubTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1736754942801222766L;

			@Override
			public void handleEvent(Event event) {
				Table table = newTaskComposite.getDetailsSideBar().getSubTaskTableViewer().getTable();
				table.remove((table.getSelectionIndex()));
				newTaskComposite.getNewTaskDetailsContainer().showGeneralInfo();
			}
		});
		removeSubTaskButton.setVisible(false);
	}
	
	public void setRemoveSubTaskButtonVisible(boolean visible) {
		removeSubTaskButton.setVisible(visible);
	}
}