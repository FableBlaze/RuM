package ee.ut.cs.rum.workspace.internal.ui.overview.projectstable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.overview.WorkspaceOverview;

public class ProjectsTableComposite extends Composite {
	private static final long serialVersionUID = 6587903782212733810L;
	
	private ProjectsTableViewer projectsTableViewer;
	private ProjectsTableFilter projectsTableFilter;

	public ProjectsTableComposite(WorkspaceOverview workspaceOverview, RumController rumController) {
		super(workspaceOverview, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		this.setLayout(new GridLayout(2, false));
		
		Label pluginsSearchLabel = new Label(this, SWT.NONE);
		pluginsSearchLabel.setText("Filter: ");
		pluginsSearchLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));

		Text projectsSearchInput = new Text(this, SWT.BORDER);
		projectsSearchInput.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		projectsSearchInput.addKeyListener(new KeyAdapter() {
			private static final long serialVersionUID = 6791829930254798544L;

			public void keyReleased(KeyEvent ke) {
				((ProjectsTableFilter) projectsTableFilter).setSearchText(projectsSearchInput.getText());
				projectsTableViewer.refresh();
			}

		});
		
		this.projectsTableViewer = new ProjectsTableViewer(workspaceOverview, this, rumController);
		projectsTableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		((GridData) this.projectsTableViewer.getTable().getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
		
		projectsTableViewer.addSelectionChangedListener(new ProjectSelectionChangedListener(workspaceOverview));
		
		this.projectsTableFilter = new ProjectsTableFilter();
		this.projectsTableViewer.addFilter(projectsTableFilter);
	}

}
