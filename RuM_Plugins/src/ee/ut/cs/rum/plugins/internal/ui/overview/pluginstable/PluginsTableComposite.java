package ee.ut.cs.rum.plugins.internal.ui.overview.pluginstable;

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
import ee.ut.cs.rum.plugins.internal.ui.overview.OverviewTabContents;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class PluginsTableComposite extends Composite {
	private static final long serialVersionUID = 6151369187697732888L;
	
	private PluginsTableViewer pluginsTableViewer;
	private ViewerFilter pluginsTableFilter;
	
	public PluginsTableComposite(OverviewTabContents overviewTabContents, PluginsManagementUI pluginsManagementUI, RumController rumController) {
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
				((PluginsTableFilter) pluginsTableFilter).setSearchText(pluginsSearchInput.getText());
		        pluginsTableViewer.refresh();
		      }

		    });
		
		this.pluginsTableViewer = new PluginsTableViewer(this, pluginsManagementUI, rumController);
		((GridData) this.pluginsTableViewer.getTable().getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
		
		this.pluginsTableFilter = new PluginsTableFilter();
		this.pluginsTableViewer.addFilter(pluginsTableFilter);
		
	}
}
