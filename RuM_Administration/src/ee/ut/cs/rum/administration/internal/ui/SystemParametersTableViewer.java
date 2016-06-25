package ee.ut.cs.rum.administration.internal.ui;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.administration.ui.SystemAdministrationUI;
import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SystemParameter;
import ee.ut.cs.rum.database.util.SystemParameterAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class SystemParametersTableViewer extends TableViewer implements RumUpdatableView {
	private static final long serialVersionUID = 73600176583676925L;

	private Display display;
	private RumController rumController;

	private List<SystemParameter> systemParameters;

	public SystemParametersTableViewer(SystemAdministrationUI systemAdministrationUI, RumController rumController) {
		super(systemAdministrationUI, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.SYSTEM_PARAMETER);

		createColumns(this);

		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		//Listening to Table dispose because dispose is not called on TableViewer
		table.addDisposeListener(new DisposeListener() {
			private static final long serialVersionUID = 7634726052580474495L;

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				rumController.unregisterView(SystemParametersTableViewer.this, ControllerEntityType.SYSTEM_PARAMETER);
			}
		});

		this.setContentProvider(new ArrayContentProvider());
		systemParameters = SystemParameterAccess.getSystemParametersDataFromDb();
		this.setInput(systemParameters);
	}

	private void createColumns(final TableViewer viewer) {
		String[] titles = { "Id", "Name", "Description", "Value (Click on value to modify)"};
		int[] bounds = { 50, 180, 300, 700 };

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
		valueColumn.setEditingSupport(new SystemParameterValueEditingSupport(viewer, rumController));
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
		if (updatedEntity instanceof SystemParameter) {
			SystemParameter systemParameter = (SystemParameter) updatedEntity;
			int updatedEntityIndex;
			switch (updateType) {
			//Both list and viewer must be updated as updates in one are not reflected automatically to other
			case MODIFIY:
				updatedEntityIndex = findSystemParameterIndex(systemParameter);
				if (updatedEntityIndex != -1) {
					systemParameters.set(updatedEntityIndex, systemParameter);
					display.asyncExec(new Runnable() {
						public void run() {
							SystemParametersTableViewer.this.replace(systemParameter, updatedEntityIndex);
						}
					});
				}
				break;
			default:
				break;
			}
		}
	}
	
	private int findSystemParameterIndex(SystemParameter systemParameter) {
		for (int i = 0; i < this.systemParameters.size(); i++) {
			if (this.systemParameters.get(i).getId()==systemParameter.getId()) {
				return i;
			}
		}
		return -1;
	}
}
