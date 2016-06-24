package ee.ut.cs.rum.administration.internal.ui;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SystemParameter;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;

public class SystemParameterValueEditingSupport extends EditingSupport {
	private static final long serialVersionUID = -5172543737241228835L;
	
	private final CellEditor editor;
	private RumController rumController;

	public SystemParameterValueEditingSupport(TableViewer viewer, RumController rumController) {
		super(viewer);
		this.editor = new TextCellEditor(viewer.getTable());
		
		this.rumController=rumController;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		String value = ((SystemParameter) element).getValue();
		if (value != null) {
			return value;
		} else {
			return "";
		}
	}

	@Override
	protected void setValue(Object element, Object userInputValue) {
		
		SystemParameter systemParameter = (SystemParameter) element;
		systemParameter.setValue(String.valueOf(userInputValue));
		rumController.changeData(ControllerUpdateType.MODIFIY, ControllerEntityType.SYSTEM_PARAMETER, systemParameter, "TODO");
	}

}
