package ee.ut.cs.rum.workspace.internal.ui.project;

import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.util.UserFileAccess;

public class UserFilesTableViewer extends TableViewer {
	private static final long serialVersionUID = -1163929902888421855L;

	public UserFilesTableViewer(ProjectOverviewExpandBar projectOverviewExpandBar) {
		super(projectOverviewExpandBar, SWT.H_SCROLL | SWT.V_SCROLL);

		createColumns(this);
		
		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		this.setContentProvider(new ArrayContentProvider());
		Long projectId = projectOverviewExpandBar.getProjectOverviewComposite().getProject().getId();
		//TODO: Duplicates the same class in overview package with only next line being different
		this.setInput(UserFileAccess.getWorkspaceUserFilesDataFromDb(projectId));
	}

	private void createColumns(UserFilesTableViewer viewer) {
		String[] titles = { "Name", "Created at"};
		int[] bounds = { 200, 125 };
		
		TableViewerColumn nameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -6627398148604308908L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return userFile.getOriginalFilename();
			}
		});
		
		TableViewerColumn createdAtColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		createdAtColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -4095576663886113023L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return new SimpleDateFormat("dd-MM-yyyy").format(userFile.getCreatedAt());
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
