package ee.ut.cs.rum.files.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.files.ui.FilesManagementUI;

public class OverviewTabContents extends Composite {
	private static final long serialVersionUID = 2017121761255826204L;

	public OverviewTabContents(FilesManagementUI filesManagementUI, RumController rumController) {
		super(filesManagementUI, SWT.NONE);
		this.setLayout(new GridLayout(2, false));
	}
}
