package ee.ut.cs.rum.workspace.internal.ui.task.details.sidebar;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.util.SubTaskAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class SubTaskTableViewer extends TableViewer implements RumUpdatableView {
	private static final long serialVersionUID = -8187726968272906469L;

	private Display display;

	private List<SubTask> subTasks;

	public SubTaskTableViewer(TaskDetailsSidebar detailsSideBar, RumController rumController) {
		super(detailsSideBar, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		this.display=Display.getCurrent();
		rumController.registerView(this, ControllerEntityType.SUBTASK);

		createColumns(this);

		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		//Listening to Table dispose because dispose is not called on TableViewer
		table.addDisposeListener(new DisposeListener() {
			private static final long serialVersionUID = 832776161208430560L;

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				rumController.unregisterView(SubTaskTableViewer.this, ControllerEntityType.SUBTASK);
			}
		});

		Task task = detailsSideBar.getTaskDetailsComposite().getTask();
		this.setContentProvider(new ArrayContentProvider());
		subTasks = SubTaskAccess.getTaskSubtasksDataFromDb(task.getId());
		this.setInput(subTasks);
	}

	private static void createColumns(final TableViewer viewer) {
		String[] titles = {"Name", "Status", "Plugin"};
		int[] bounds = {200, 100, 200};

		TableViewerColumn nameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 859768103676685673L;

			@Override
			public String getText(Object element) {
				SubTask subTask = (SubTask) element;
				return subTask.getName();
			}
		});

		TableViewerColumn progressColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		progressColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 859768103676685673L;

			@Override
			public String getText(Object element) {
				SubTask subTask = (SubTask) element;
				return subTask.getStatus().toString();
			}
		});

		TableViewerColumn pluginColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		pluginColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 859768103676685673L;

			@Override
			public String getText(Object element) {
				SubTask subTask = (SubTask) element;
				return subTask.getPlugin().getBundleName();
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
		if (updatedEntity instanceof SubTask) {
			SubTask subTask = (SubTask) updatedEntity;
			int updatedEntityIndex;
			switch (updateType) {
			//Subtasks will not be added nor removed after task creation
			case MODIFIY:
				updatedEntityIndex = findSubTaskIndex(subTask);
				if (updatedEntityIndex != -1) {
					subTasks.set(updatedEntityIndex, subTask);
					display.asyncExec(new Runnable() {
						public void run() {
							SubTaskTableViewer.this.replace(subTask, updatedEntityIndex);
						}
					});
				}
				break;
			default:
				break;
			}
		}

	}

	private int findSubTaskIndex(SubTask subTask) {
		for (int i = 0; i < this.subTasks.size(); i++) {
			if (this.subTasks.get(i).getId()==subTask.getId()) {
				return i;
			}
		}
		return -1;
	}
}
