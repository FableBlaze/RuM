package ee.ut.cs.rum.plugins.internal.ui.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.plugins.internal.Activator;

public final class PluginsTable {
	private PluginsTable() {
	}

	public static void createPluginsTable(Composite parent, CTabFolder pluginsManagementTabs, List<Plugin> plugins) {
		TableViewer viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		createColumns(parent, viewer, pluginsManagementTabs);
		
		final Table table = viewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(plugins);
	}


	private static void createColumns(final Composite parent, final TableViewer viewer, CTabFolder pluginsManagementTabs) {

		String[] titles = { "Name", "Description", "Details"};
		int[] bounds = { 200, 400, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Plugin p = (Plugin) element;
				return p.getName();
			}
		});

		TableViewerColumn col2 = createTableViewerColumn(titles[1], bounds[1], 1, viewer);
		col2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Plugin p = (Plugin) element;
				return p.getDescription();
			}
		});

		TableViewerColumn col3 = createTableViewerColumn(titles[2], bounds[2], 2, viewer);
		col3.setLabelProvider(new ColumnLabelProvider() {
			//TODO: The buttons are probably not disposed properly
			Map<Object, Button> buttons = new HashMap<Object, Button>();

			@Override
			public void update(ViewerCell cell) {

				TableItem item = (TableItem) cell.getItem();
				Button button;
				if(buttons.containsKey(cell.getElement())) {
					button = buttons.get(cell.getElement());
				}
				else {
					button = new Button((Composite) cell.getViewerRow().getControl(),SWT.NONE);
					button.setText("Details");
					button.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent arg0) {
							CTabItem item = new CTabItem (pluginsManagementTabs, SWT.NONE | SWT.CLOSE);
							item.setText ("Plugin x");
							Label label = new Label (pluginsManagementTabs, SWT.PUSH);
							label.setText ("Plugin details");
							item.setControl (label);
							pluginsManagementTabs.setSelection(pluginsManagementTabs.getItemCount()-1);
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent arg0) {
						}
					});
					buttons.put(cell.getElement(), button);
				}
				TableEditor editor = new TableEditor(item.getParent());
				editor.grabHorizontal  = true;
				editor.grabVertical = true;
				editor.setEditor(button , item, cell.getColumnIndex());
				editor.layout();
			}
		});

	}

	private static TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber, final TableViewer viewer) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		return viewerColumn;
	}

}
