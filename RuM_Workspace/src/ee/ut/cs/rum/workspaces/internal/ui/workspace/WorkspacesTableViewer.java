package ee.ut.cs.rum.workspaces.internal.ui.workspace;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.database.domain.Workspace;
import ee.ut.cs.rum.database.util.WorkspaceAccess;


public class WorkspacesTableViewer extends TableViewer {
	private static final long serialVersionUID = -4856474442900733174L;

	public WorkspacesTableViewer(WorkspacesOverview workspacesOverview) {
		super(workspacesOverview, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		
		createColumns(this);
		
		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		this.setContentProvider(new ArrayContentProvider());
		this.setInput(WorkspaceAccess.getWorkspacesDataFromDb());
	}
	
	private static void createColumns(final TableViewer viewer) {
		String[] titles = { "Name", "Description"};
		int[] bounds = { 200, 700 };

		TableViewerColumn nameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -6627398148604308908L;

			@Override
			public String getText(Object element) {
				Workspace workspace = (Workspace) element;
				return workspace.getName();
			}
		});

		TableViewerColumn descriptionColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		descriptionColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 3978700864805271863L;

			@Override
			public String getText(Object element) {
				Workspace workspace = (Workspace) element;
				return workspace.getDescription();
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
