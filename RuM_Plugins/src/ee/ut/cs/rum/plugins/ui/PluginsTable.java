package ee.ut.cs.rum.plugins.ui;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.Activator;

public final class PluginsTable {
	private PluginsTable() {
	}

	public static Table createPluginsTable(Composite parent) {
		TableViewer viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));

		viewer.setContentProvider(new ArrayContentProvider());

		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select p from Plugin p order by p.id");
		List<Plugin> plugins = query.getResultList();
		
		viewer.setInput(plugins);
		
		
		return null;

	}


	private static void createColumns(final Composite parent, final TableViewer viewer) {

		String[] titles = { "Name", "Description" };
		int[] bounds = { 200, 400 };

		// first column is for the name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Plugin p = (Plugin) element;
				return p.getName();
			}
		});

		// second column is for the description
		TableViewerColumn col2 = createTableViewerColumn(titles[1], bounds[1], 1, viewer);
		col2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Plugin p = (Plugin) element;
				return p.getDescription();
			}
		});

	}

	private static TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber, final TableViewer viewer) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

}
