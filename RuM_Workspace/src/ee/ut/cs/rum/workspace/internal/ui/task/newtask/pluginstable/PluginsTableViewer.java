package ee.ut.cs.rum.workspace.internal.ui.task.newtask.pluginstable;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.util.PluginAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class PluginsTableViewer extends TableViewer implements RumUpdatableView {
	private static final long serialVersionUID = -1527326670580351367L;

	private Display display;

	private List<Plugin> plugins;

	public PluginsTableViewer(PluginsTableComposite pluginsTableComposite, RumController rumController) {
		super(pluginsTableComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		this.display=Display.getCurrent();
		rumController.registerView(this, ControllerEntityType.PLUGIN);

		createColumns(this);

		final Table table = this.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		//Listening to Table dispose because dispose is not called on TableViewer
		table.addDisposeListener(new DisposeListener() {
			private static final long serialVersionUID = 832776161208430560L;

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				rumController.unregisterView(PluginsTableViewer.this, ControllerEntityType.PLUGIN);
			}
		});

		this.setContentProvider(new ArrayContentProvider());
		plugins = PluginAccess.getPluginsDataFromDb();
		this.setInput(plugins);
	}

	private static void createColumns(final TableViewer viewer) {
		String[] titles = { "Name", "Version"};
		int[] bounds = { 200, 75 };

		TableViewerColumn nameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 859768103676685673L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return plugin.getBundleName();
			}
		});

		TableViewerColumn versionColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		versionColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 7951912026066172553L;

			@Override
			public String getText(Object element) {
				Plugin plugin = (Plugin) element;
				return plugin.getBundleVersion();
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
