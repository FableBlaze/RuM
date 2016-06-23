package ee.ut.cs.rum.workspace.internal.ui.task.details.sidebar;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.database.domain.SubTask;

public class SubTaskTableViewer extends TableViewer {
	private static final long serialVersionUID = -8187726968272906469L;

	public SubTaskTableViewer(DetailsSidebar detailsSideBar) {
		super(detailsSideBar, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		
		createColumns(this);
		
		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
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
}
