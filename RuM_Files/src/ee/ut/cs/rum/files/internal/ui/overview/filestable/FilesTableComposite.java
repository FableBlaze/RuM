package ee.ut.cs.rum.files.internal.ui.overview.filestable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.files.internal.ui.overview.OverviewTabContents;
import ee.ut.cs.rum.files.ui.FilesManagementUI;

public class FilesTableComposite extends Composite {
	private static final long serialVersionUID = -2073488416084654668L;

	public FilesTableComposite(OverviewTabContents overviewTabContents, FilesManagementUI filesManagementUI, RumController rumController) {
		super(overviewTabContents, SWT.NONE);
	}
}
