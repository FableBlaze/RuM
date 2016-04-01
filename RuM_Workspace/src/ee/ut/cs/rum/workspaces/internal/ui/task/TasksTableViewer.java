package ee.ut.cs.rum.workspaces.internal.ui.task;

import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.workspaces.internal.ui.workspace.WorkspaceDetailsTabContents;

public class TasksTableViewer extends TableViewer {
	private static final long serialVersionUID = -3241294193014510267L;

	public TasksTableViewer(WorkspaceDetailsTabContents workspaceDetails) {
		super(workspaceDetails, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		
		createColumns(this);
		
		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		this.setContentProvider(new ArrayContentProvider());
		this.setInput(TaskAccess.getWorkspaceTasksDataFromDb(workspaceDetails.getWorkspace().getId()));
	}
	
	private static void createColumns(final TableViewer viewer) {
		String[] titles = { "Name", "Status", "Plugin", "Description", "Created At"};
		int[] bounds = { 200, 100, 200, 500, 100 };
		
		TableViewerColumn nameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 3975618622435761248L;

			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return task.getName();
			}
		});
		
		TableViewerColumn statusColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		statusColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 8686065541376545187L;

			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return task.getStatus();
			}
		});
		
		TableViewerColumn pluginColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		pluginColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -954135701911902297L;

			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return task.getPluginId().toString();
			}
		});
		
		TableViewerColumn descriptionColumn = createTableViewerColumn(titles[3], bounds[3], viewer);
		descriptionColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 5398613746904037365L;

			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return task.getDescription();
			}
		});
		
		TableViewerColumn createdAtColumn = createTableViewerColumn(titles[4], bounds[4], viewer);
		createdAtColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 476293081865151219L;

			@Override
			public String getText(Object element) {
				Task task = (Task) element;
				return new SimpleDateFormat("dd-MM-yyyy").format(task.getCreatedAt());
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

}
