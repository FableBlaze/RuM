package ee.ut.cs.rum.plugins.internal.ui.overview.pluginstable;

import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.util.PluginAccess;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class PluginsTableViewer extends TableViewer {
	private static final long serialVersionUID = -2085870762932626509L;

	public PluginsTableViewer(PluginsTableComposite pluginsTableComposite, PluginsManagementUI pluginsManagementUI) {
		super(pluginsTableComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		createColumns(this, pluginsManagementUI);

		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		this.setContentProvider(new ArrayContentProvider());
		this.setInput(PluginAccess.getPluginsDataFromDb());
	}


	private static void createColumns(final TableViewer viewer, PluginsManagementUI pluginsManagementUI) {
		String[] titles = { "Id", "Name", "Description", "Version", "Vendor", "Uploaded by", "Uploaded at", "Details"};
		int[] bounds = { 50, 200, 400, 75, 200, 125, 125, 75 };

		TableViewerColumn idColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		idColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 5872575516853111364L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return plugin.getId().toString();
			}
		});

		TableViewerColumn nameColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 859768103676685673L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return plugin.getBundleName();
			}
		});

		TableViewerColumn descriptionColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		descriptionColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -6832957480315357307L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return plugin.getBundleDescription();
			}
		});

		TableViewerColumn versionColumn = createTableViewerColumn(titles[3], bounds[3], viewer);
		versionColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 7951912026066172553L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return plugin.getBundleVersion();
			}
		});

		TableViewerColumn vendorColumn = createTableViewerColumn(titles[4], bounds[4], viewer);
		vendorColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 1678694658429600979L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return plugin.getBundleVendor();
			}
		});

		TableViewerColumn uploadedByColumn = createTableViewerColumn(titles[5], bounds[5], viewer);
		uploadedByColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -8587147235926895690L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return plugin.getUploadedBy();
			}
		});

		TableViewerColumn uploadedAtColumn = createTableViewerColumn(titles[6], bounds[6], viewer);
		uploadedAtColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -6962864367578702460L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return new SimpleDateFormat("dd-MM-yyyy").format(plugin.getUploadedAt());
			}
		});

		TableViewerColumn detailsButtonColumn = createTableViewerColumn(titles[7], bounds[7], viewer);
		detailsButtonColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 4559441071410857663L;
			
			@Override
			public void update(ViewerCell cell) {
				TableItem item = (TableItem) cell.getItem();
				Plugin plugin = (Plugin) cell.getElement();
				PluginDetailsButton pluginDetailsButton = new PluginDetailsButton((Composite) cell.getViewerRow().getControl(), plugin, pluginsManagementUI);
				
				item.addDisposeListener(new DisposeListener() {
					private static final long serialVersionUID = -927877657358384078L;

					@Override
					public void widgetDisposed(DisposeEvent arg0) {
						//TODO: Check why dispose() keeps the button in the UI
						pluginDetailsButton.setVisible(false);
						pluginDetailsButton.dispose();
					}
				});
				
				TableEditor editor = new TableEditor(item.getParent());
				editor.grabHorizontal  = true;
				editor.grabVertical = true;
				editor.setEditor(pluginDetailsButton , item, cell.getColumnIndex());
				editor.layout();
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
