package ee.ut.cs.rum.workspace.internal.ui.project.taskstable;

import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class TasksTableViewer extends TableViewer implements RumUpdatableView {
	private static final long serialVersionUID = 969638099941371909L;
	
	private Display display;

	private List<Task> tasks;

	public TasksTableViewer(TasksTableComposite tasksTableComposite, RumController rumController) {
		super(tasksTableComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		
		this.display=Display.getCurrent();
		rumController.registerView(this, ControllerEntityType.TASK);
		
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
		tasks = TaskAccess.getProjectTasksDataFromDb(tasksTableComposite.getProjectOverviewComposite().getProject().getId());
		this.setInput(tasks);
		
	}
	
	private void createColumns(TasksTableViewer viewer) {
		String[] titles = { "Name", "Sub-tasks", "Last updated", "Created"};
		int[] bounds = { 200, 100, 125, 125 };

		TableViewerColumn nameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 8328614399553991618L;

			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return task.getName();
			}
		});

		TableViewerColumn subtasksColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		subtasksColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -2620995247851266199L;

			@Override
			public String getText(Object element) {
				return "TODO";
			}
		});

		TableViewerColumn lastUpdatedColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		lastUpdatedColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 3624836381657429104L;

			@Override
			public String getText(Object element) {
				return "TODO";
			}
		});

		TableViewerColumn createdColumn = createTableViewerColumn(titles[3], bounds[3], viewer);
		createdColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 5740556650561563945L;

			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(task.getCreatedAt());
			}
		});
		
	}

	private static TableViewerColumn createTableViewerColumn(String title, int bound, final TableViewer viewer) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		return viewerColumn;
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		// TODO Auto-generated method stub
		
	}
}
