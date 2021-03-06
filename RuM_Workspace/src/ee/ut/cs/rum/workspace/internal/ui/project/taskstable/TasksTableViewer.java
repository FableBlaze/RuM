package ee.ut.cs.rum.workspace.internal.ui.project.taskstable;

import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.enums.SubTaskStatus;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class TasksTableViewer extends TableViewer implements RumUpdatableView {
	private static final long serialVersionUID = 969638099941371909L;
	
	private Display display;

	private TasksTableComposite tasksTableComposite;
	private List<Task> tasks;

	public TasksTableViewer(TasksTableComposite tasksTableComposite, RumController rumController) {
		super(tasksTableComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		
		this.display=Display.getCurrent();
		rumController.registerView(this, ControllerEntityType.TASK);
		
		this.tasksTableComposite=tasksTableComposite;
		
		ColumnViewerToolTipSupport.enableFor(this);
		createColumns(this);
		
		final Table table = this.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		//Listening to Table dispose because dispose is not called on TableViewer
		table.addDisposeListener(new DisposeListener() {
			private static final long serialVersionUID = 832776161208430560L;

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				rumController.unregisterView(TasksTableViewer.this, ControllerEntityType.TASK);
			}
		});
		
		this.setContentProvider(new ArrayContentProvider());
		tasks = TaskAccess.getProjectTasksDataFromDb(tasksTableComposite.getProjectOverview().getProject().getId());
		this.setInput(tasks);
		
	}
	
	private void createColumns(TasksTableViewer viewer) {
		String[] titles = { "Name", "Processed", "Status", "Last change at"};
		int[] bounds = { 200, 100, 125, 175 };

		TableViewerColumn nameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 8328614399553991618L;

			@Override
			public String getToolTipText(Object element) {
				Task task = (Task) element;
				return task.getDescription();
			}
			
			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return task.getName();
			}
		});

		TableViewerColumn subTasksProcessedColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		subTasksProcessedColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -2620995247851266199L;

			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				if (task.getSubTasks()!=null) {
					int subTasksTotal = task.getSubTasks().size();
					int completedSubTasks = 0;
					for (SubTask subTask : task.getSubTasks()) {
						if (subTask.getStatus()==SubTaskStatus.DONE || subTask.getStatus()==SubTaskStatus.FAILED) {
							completedSubTasks+=1;
						}
					}
					return Integer.toString(completedSubTasks) + " of " + Integer.toString(subTasksTotal);
				} else {
					return "";
				}
			}
		});

		TableViewerColumn createdColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		createdColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 5740556650561563945L;

			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return task.getStatus().toString();
			}
		});
		
		TableViewerColumn lastModifiedAtColumn = createTableViewerColumn(titles[3], bounds[3], viewer);
		lastModifiedAtColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 3624836381657429104L;
			
			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(task.getLastModifiedAt());
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final TableViewer viewer) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		return viewerColumn;
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof Task) {
			Task task = (Task) updatedEntity;
			if (task.getProject().getId() == tasksTableComposite.getProjectOverview().getProject().getId()) {
				int updatedEntityIndex;
				switch (updateType) {
				//Both list and viewer must be updated as updates in one are not reflected automatically to other
				case CREATE:
					tasks.add(task);
					display.asyncExec(new Runnable() {
						public void run() {
							TasksTableViewer.this.add(task);
						}
					});
					break;
				case MODIFIY:
					updatedEntityIndex = findTaskIndex(task);
					if (updatedEntityIndex != -1) {
						tasks.set(updatedEntityIndex, task);
						display.asyncExec(new Runnable() {
							public void run() {
								TasksTableViewer.this.replace(task, updatedEntityIndex);
							}
						});
					}
					break;
				case DELETE:
					updatedEntityIndex = findTaskIndex(task);
					if (updatedEntityIndex != -1) {
						synchronized(this){
							display.asyncExec(new Runnable() {
								public void run() {
									TasksTableViewer.this.remove(tasks.get(updatedEntityIndex));
									tasks.remove(updatedEntityIndex);
								}
							});
						}
					}
					break;
				default:
					break;
				}
			}
		}
	}
	
	private int findTaskIndex(Task task) {
		for (int i = 0; i < this.tasks.size(); i++) {
			if (this.tasks.get(i).getId()==task.getId()) {
				return i;
			}
		}
		return -1;
	}
}
