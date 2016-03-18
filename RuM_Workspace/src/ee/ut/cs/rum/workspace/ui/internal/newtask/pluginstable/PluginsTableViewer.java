package ee.ut.cs.rum.workspace.ui.internal.newtask.pluginstable;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.util.PluginAccess;

public class PluginsTableViewer extends TableViewer {
	private static final long serialVersionUID = -2467863870428087617L;

	public PluginsTableViewer(PluginsTableComposite pluginsTableComposite) {
		super(pluginsTableComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		createColumns(this);

		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		this.setContentProvider(new ArrayContentProvider());
		this.setInput(PluginAccess.getPluginsDataFromDb());	
	}


	private static void createColumns(final TableViewer viewer) {
		String[] titles = { "Name", "Description", "Version"};
		int[] bounds = { 200, 400, 75 };

		TableViewerColumn nameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 859768103676685673L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return plugin.getName();
			}
		});

		TableViewerColumn descriptionColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		descriptionColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -6832957480315357307L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return plugin.getDescription();
			}
		});

		TableViewerColumn versionColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		versionColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 7951912026066172553L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return plugin.getVersion();
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
