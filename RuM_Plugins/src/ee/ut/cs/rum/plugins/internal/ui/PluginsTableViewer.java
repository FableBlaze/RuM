package ee.ut.cs.rum.plugins.internal.ui;

import java.util.HashMap;
import java.util.Map;

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
import ee.ut.cs.rum.plugins.internal.util.PluginsData;

public class PluginsTableViewer extends TableViewer {
	private static final long serialVersionUID = -2085870762932626509L;

	public PluginsTableViewer(OverviewTabContents overviewTabContents, CTabFolder pluginsManagementTabs) {
		super(overviewTabContents, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		
		createColumns(this, pluginsManagementTabs);
		
		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		this.setContentProvider(new ArrayContentProvider());
		this.setInput(PluginsData.getPluginsDataFromDb());
	}
	
	
	private static void createColumns(final TableViewer viewer, CTabFolder pluginsManagementTabs) {
		String[] titles = { "Name", "Description", "Details"};
		int[] bounds = { 200, 400, 100 };

		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0, viewer);
		col.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 5872575516853111364L;

			@Override
			public String getText(Object element) {
				Plugin p = (Plugin) element;
				return p.getName();
			}
		});

		TableViewerColumn col2 = createTableViewerColumn(titles[1], bounds[1], 1, viewer);
		col2.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 859768103676685673L;

			@Override
			public String getText(Object element) {
				Plugin p = (Plugin) element;
				return p.getDescription();
			}
		});

		TableViewerColumn col3 = createTableViewerColumn(titles[2], bounds[2], 2, viewer);
		col3.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -8762829711174270692L;
			
			//TODO: The buttons are probably not disposed properly
			Map<Object, PluginDetailsButton> buttons = new HashMap<Object, PluginDetailsButton>();

			@Override
			public void update(ViewerCell cell) {
				TableItem item = (TableItem) cell.getItem();
				PluginDetailsButton button;
				if(buttons.containsKey(cell.getElement())) {
					button = buttons.get(cell.getElement());
				}
				else {
					Plugin p = (Plugin) cell.getElement();
					button = new PluginDetailsButton((Composite) cell.getViewerRow().getControl(),p.getId());
					button.setText("Details");
					button.addSelectionListener(new SelectionListener() {
						private static final long serialVersionUID = 2256156491864328920L;

						@Override
						public void widgetSelected(SelectionEvent arg0) {
							CTabItem item = new CTabItem (pluginsManagementTabs, SWT.NONE | SWT.CLOSE);
							item.setText ("Plugin x");
							Label label = new Label (pluginsManagementTabs, SWT.PUSH);
							label.setText ("Plugin details");
							item.setControl (label);
							pluginsManagementTabs.setSelection(pluginsManagementTabs.getItemCount()-1);
							
							Activator.getLogger().info("Opened deatils of plugin with id: " + ((PluginDetailsButton) arg0.getSource()).getPluginId());
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
