package ee.ut.cs.rum.administration.internal.ui;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.administration.internal.util.SystemParametersData;
import ee.ut.cs.rum.administration.ui.SystemAdministrationUI;
import ee.ut.cs.rum.database.domain.SystemParameter;

public class SystemParametersTableViewer extends TableViewer {
	private static final long serialVersionUID = 73600176583676925L;

	public SystemParametersTableViewer(SystemAdministrationUI systemAdministrationUI) {
		super(systemAdministrationUI, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		
		createColumns(this);
		
		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		this.setContentProvider(new ArrayContentProvider());
		this.setInput(SystemParametersData.getSystemParametersDataFromDb());
	}

	private void createColumns(final TableViewer viewer) {
		String[] titles = { "Id", "Name", "Description", "Value (Click on value to modify)"};
		int[] bounds = { 50, 100, 300, 700 };
		
		TableViewerColumn idColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		idColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -4912839519372895708L;

			@Override
			public String getText(Object element) {
				SystemParameter systemParameter = (SystemParameter) element;
				return systemParameter.getId().toString();
			}
		});
		
		TableViewerColumn nameColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 9095454663591486892L;

			@Override
			public String getText(Object element) {
				SystemParameter systemParameter = (SystemParameter) element;
				return systemParameter.getName();
			}
		});
		
		TableViewerColumn descriptionColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		descriptionColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -8466240551635651073L;

			@Override
			public String getText(Object element) {
				SystemParameter systemParameter = (SystemParameter) element;
				return systemParameter.getDescription();
			}
		});
		
		TableViewerColumn valueColumn = createTableViewerColumn(titles[3], bounds[3], viewer);
		valueColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -7106589879886336218L;

			@Override
			public String getText(Object element) {
				SystemParameter systemParameter = (SystemParameter) element;
				return systemParameter.getValue();
			}
		});
		valueColumn.setEditingSupport(new SystemParameterValueEditingSupport(viewer));
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
