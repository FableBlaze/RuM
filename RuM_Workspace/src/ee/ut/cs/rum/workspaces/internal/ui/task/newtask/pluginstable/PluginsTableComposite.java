package ee.ut.cs.rum.workspaces.internal.ui.task.newtask.pluginstable;

import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.workspaces.internal.ui.task.newtask.dialog.NewTaskDialogShell;

public class PluginsTableComposite extends Composite {
	private static final long serialVersionUID = 8625656134570039043L;

	private PluginsTableViewer pluginsTableViewer;
	private ViewerFilter pluginsTableFilter;

	public PluginsTableComposite(NewTaskDialogShell newTaskDialogShell) {
		super(newTaskDialogShell, SWT.NONE);

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
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

		this.pluginsTableViewer = new PluginsTableViewer(this);
		((GridData) this.pluginsTableViewer.getTable().getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;

		pluginsTableViewer.addSelectionChangedListener(new PluginSelectionChangedListener(newTaskDialogShell));

		this.pluginsTableFilter = new PluginsTableFilter();
		this.pluginsTableViewer.addFilter(pluginsTableFilter);
	}
	
	public PluginsTableViewer getPluginsTableViewer() {
		return pluginsTableViewer;
	}

}
