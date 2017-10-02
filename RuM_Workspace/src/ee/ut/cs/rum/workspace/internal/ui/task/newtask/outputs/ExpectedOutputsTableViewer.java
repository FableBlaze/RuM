package ee.ut.cs.rum.workspace.internal.ui.task.newtask.outputs;


import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.domain.UserFileType;

public class ExpectedOutputsTableViewer extends TableViewer {
	private static final long serialVersionUID = -6967570486106206640L;

	public ExpectedOutputsTableViewer(ExpectedOutputsTableComposite expectedOutputsTableComposite) {
		super(expectedOutputsTableComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		createColumns(this);
		
		final Table table = this.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		this.setContentProvider(new ArrayContentProvider());
	}

	private void createColumns(final TableViewer viewer) {
		String[] titles = { "SubTask", "Name", "Types"};
		int[] bounds = { 200, 200, 200 };

		TableViewerColumn subTaskNameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		subTaskNameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 859768103676685673L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return userFile.getSubTask().getName();
			}
		});

		TableViewerColumn fileNameColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		fileNameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 7951912026066172553L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return userFile.getOriginalFilename();
			}
		});
		
		TableViewerColumn fileTypesColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		fileTypesColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 7951912026066172553L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				String fileTypeString = "";
				for (UserFileType userFileType : userFile.getUserFileTypes()) {
					fileTypeString+=userFileType.getTypeName() + ", ";
				}
				if (!fileTypeString.isEmpty()) {
					fileTypeString=fileTypeString.substring(0, fileTypeString.length()-2);
				}
				return fileTypeString;
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

}
