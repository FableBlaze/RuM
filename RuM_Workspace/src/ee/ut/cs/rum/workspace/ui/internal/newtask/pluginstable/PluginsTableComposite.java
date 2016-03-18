package ee.ut.cs.rum.workspace.ui.internal.newtask.pluginstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.workspace.internal.Activator;
import ee.ut.cs.rum.workspace.ui.internal.newtask.NewTaskComposite;

public class PluginsTableComposite extends Composite {
	private static final long serialVersionUID = 8625656134570039043L;
	
	private PluginsTableViewer pluginsTableViewer;
	private ViewerFilter pluginsTableFilter;
	
	public PluginsTableComposite(NewTaskComposite newTaskComposite) {
		super(newTaskComposite, SWT.NONE);
		
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
		
		pluginsTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		    public void selectionChanged(final SelectionChangedEvent event) {
		        IStructuredSelection selection = (IStructuredSelection)event.getSelection();
		        Plugin plugin = (Plugin) selection.getFirstElement();
		        newTaskComposite.getSelectedPluginInfo().updateSelectedPluginInfo(plugin);
		        //TODO: Check if plugin provided services are available
		        if (plugin!=null && !isPluginInstalled(plugin)) {
		        	try {
		        		Bundle temporaryBundle = Activator.getContext().installBundle("file:///" + plugin.getFileLocation());
		        		temporaryBundle.start();
						temporaryBundle.stop();
		        		temporaryBundle.uninstall();
					} catch (BundleException e) {
						Activator.getLogger().info("Failed loading plugin: " + plugin.toString());
						newTaskComposite.getSelectedPluginInfo().updateSelectedPluginInfo(null);
					}
		        }
		    }
		});
		
		this.pluginsTableFilter = new PluginsTableFilter();
		this.pluginsTableViewer.addFilter(pluginsTableFilter);
	}
	
	//TODO: This check seems to already be done by the framework
	private boolean isPluginInstalled(Plugin plugin) {
		for (Bundle b : Activator.getContext().getBundles()) {
			if (b.getLocation().equals("file:///" + plugin.getFileLocation())) {
				return true;
			}
		}
		return false;
	}

}
