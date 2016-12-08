package ee.ut.cs.rum.files.internal.ui.overview.filestable;

import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.files.internal.ui.overview.OverviewTabContents;
import ee.ut.cs.rum.files.ui.FilesManagementUI;
import ee.ut.cs.rum.files.internal.ui.overview.filestable.FilesTableFilter;
import ee.ut.cs.rum.files.internal.ui.overview.filestable.FilesTableViewer;
import ee.ut.cs.rum.files.internal.ui.overview.filestable.FileRowDoubleClickListener;

public class FilesTableComposite extends Composite {
	private static final long serialVersionUID = -2073488416084654668L;
	
	private FilesTableViewer filesTableViewer;
	private ViewerFilter filesTableFilter;

	public FilesTableComposite(OverviewTabContents overviewTabContents, FilesManagementUI filesManagementUI, RumController rumController) {
		super(overviewTabContents, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2, false));
		
		Label pluginsSearchLabel = new Label(this, SWT.NONE);
		pluginsSearchLabel.setText("Filter: ");
		
		Text pluginsSearchInput = new Text(this, SWT.BORDER);
		pluginsSearchInput.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		pluginsSearchInput.addKeyListener(new KeyAdapter() {
		    private static final long serialVersionUID = 6791829930254798544L;

			public void keyReleased(KeyEvent ke) {
				((FilesTableFilter) filesTableFilter).setSearchText(pluginsSearchInput.getText());
		        filesTableViewer.refresh();
		      }

		    });
		
		this.filesTableViewer = new FilesTableViewer(this, filesManagementUI, rumController);
		((GridData) this.filesTableViewer.getTable().getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
		
		this.filesTableFilter = new FilesTableFilter();
		this.filesTableViewer.addFilter(filesTableFilter);
		
		this.filesTableViewer.addDoubleClickListener(new FileRowDoubleClickListener(filesManagementUI, rumController));
	}
}
