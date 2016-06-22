package ee.ut.cs.rum.workspace.internal.ui.task.newtask.pluginstable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskSubTaskInfo;

public class PluginsTableComposite extends Composite {
	private static final long serialVersionUID = 2086408059969247136L;
	
	private PluginsTableViewer pluginsTableViewer;
	private PluginsTableFilter pluginsTableFilter;
	private NewTaskSubTaskInfo newTaskSubTaskInfo;

	public PluginsTableComposite(NewTaskSubTaskInfo newTaskSubTaskInfo, RumController rumController) {
		super(newTaskSubTaskInfo, SWT.NONE);
		
		this.newTaskSubTaskInfo=newTaskSubTaskInfo;
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		this.setLayout(new GridLayout(2, false));
		
		Label pluginSearchLabel = new Label(this, SWT.NONE);
		pluginSearchLabel.setText("Filter plugins: ");
		pluginSearchLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		
		Text pluginSearchInput = new Text(this, SWT.BORDER);
		pluginSearchInput.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		pluginSearchInput.addKeyListener(new KeyAdapter() {
			private static final long serialVersionUID = -540605670370011877L;

			public void keyReleased(KeyEvent ke) {
				((PluginsTableFilter) pluginsTableFilter).setSearchText(pluginSearchInput.getText());
				pluginsTableViewer.refresh();
			}
		});
		
		this.pluginsTableViewer = new PluginsTableViewer(this, rumController);
		pluginsTableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		((GridData) this.pluginsTableViewer.getTable().getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
		
		pluginsTableViewer.addSelectionChangedListener(new PluginSelectionChangedListener(newTaskSubTaskInfo));
		
		this.pluginsTableFilter = new PluginsTableFilter();
		this.pluginsTableViewer.addFilter(pluginsTableFilter);
	}
	
	public NewTaskSubTaskInfo getNewTaskSubTaskInfo() {
		return newTaskSubTaskInfo;
	}
	
	public PluginsTableViewer getPluginsTableViewer() {
		return pluginsTableViewer;
	}

}
