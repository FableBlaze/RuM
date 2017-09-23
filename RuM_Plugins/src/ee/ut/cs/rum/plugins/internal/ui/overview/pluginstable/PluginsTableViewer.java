package ee.ut.cs.rum.plugins.internal.ui.overview.pluginstable;

import java.text.SimpleDateFormat;
import java.util.List;

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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.util.PluginAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class PluginsTableViewer extends TableViewer implements RumUpdatableView {
	private static final long serialVersionUID = -2085870762932626509L;

	private RumController rumController;
	private Display display;
	
	private List<Plugin> plugins;

	public PluginsTableViewer(PluginsTableComposite pluginsTableComposite, PluginsManagementUI pluginsManagementUI, RumController rumController) {
		super(pluginsTableComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		this.rumController=rumController;
		this.display=Display.getCurrent();
		rumController.registerView(this, ControllerEntityType.PLUGIN);

		createColumns(this, pluginsManagementUI);

		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		//Listening to Table dispose because dispose is not called on TableViewer
		table.addDisposeListener(new DisposeListener() {
			private static final long serialVersionUID = 7634726052580474495L;

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				rumController.unregisterView(PluginsTableViewer.this, ControllerEntityType.PLUGIN);
			}
		});

		this.setContentProvider(new ArrayContentProvider());
		this.plugins=PluginAccess.getAllPluginsDataFromDb();
		this.setInput(plugins);
	}


	private void createColumns(final TableViewer viewer, PluginsManagementUI pluginsManagementUI) {
		String[] titles = { "Id", "Name", "Description", "Version", "Vendor", "Uploaded at", "Enabled", "Details"};
		int[] bounds = { 50, 200, 400, 75, 200, 125, 100, 80 };

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

		TableViewerColumn uploadedAtColumn = createTableViewerColumn(titles[5], bounds[5], viewer);
		uploadedAtColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -6962864367578702460L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return new SimpleDateFormat("dd-MM-yyyy").format(plugin.getCreatedAt());
			}
		});
		
		TableViewerColumn enabledColumn = createTableViewerColumn(titles[6], bounds[6], viewer);
		enabledColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -6962864367578702460L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				if (plugin.getEnabled()) {
					return "Enabled";
				} else {
					return "Disabled";
				}
			}
		});

		TableViewerColumn detailsButtonColumn = createTableViewerColumn(titles[7], bounds[7], viewer);
		detailsButtonColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 4559441071410857663L;

			@Override
			public void update(ViewerCell cell) {
				TableItem item = (TableItem) cell.getItem();
				Plugin plugin = (Plugin) cell.getElement();
				PluginDetailsButton pluginDetailsButton = new PluginDetailsButton((Composite) cell.getViewerRow().getControl(), plugin, pluginsManagementUI, rumController);

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

	private TableViewerColumn createTableViewerColumn(String title, int bound, final TableViewer viewer) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		return viewerColumn;
	}


	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof Plugin) {
			Plugin plugin = (Plugin) updatedEntity;
			int updatedEntityIndex;
			switch (updateType) {
			//Both list and viewer must be updated as updates in one are not reflected automatically to other
			case CREATE:
				plugins.add(plugin);
				display.asyncExec(new Runnable() {
					public void run() {
						PluginsTableViewer.this.add(plugin);
					}
				});
				break;
			case MODIFIY:
				updatedEntityIndex = findPluginIndex(plugin);
				if (updatedEntityIndex != -1) {
					plugins.set(updatedEntityIndex, plugin);
					display.asyncExec(new Runnable() {
						public void run() {
							PluginsTableViewer.this.replace(plugin, updatedEntityIndex);
						}
					});
				}
				break;
			case DELETE:
				updatedEntityIndex = findPluginIndex(plugin);
				if (updatedEntityIndex != -1) {
					synchronized(this){
						display.asyncExec(new Runnable() {
							public void run() {
								PluginsTableViewer.this.remove(plugins.get(updatedEntityIndex));
								plugins.remove(updatedEntityIndex);
							}
						});
					}
				}
				break;
			default:
				break;
			}
		}
	}

	private int findPluginIndex(Plugin plugin) {
		for (int i = 0; i < this.plugins.size(); i++) {
			if (this.plugins.get(i).getId()==plugin.getId()) {
				return i;
			}
		}
		return -1;
	}
}
