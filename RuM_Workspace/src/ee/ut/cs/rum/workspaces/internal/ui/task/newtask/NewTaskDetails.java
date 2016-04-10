package ee.ut.cs.rum.workspaces.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.workspaces.internal.ui.task.PluginInfoComposite;
import ee.ut.cs.rum.workspaces.internal.ui.task.newtask.pluginstable.PluginsTableComposite;
import ee.ut.cs.rum.workspaces.internal.ui.workspace.WorkspaceTabFolder;

public class NewTaskDetails extends Composite {
	private static final long serialVersionUID = 1902692600726551589L;

	//private Composite content;
	private WorkspaceTabFolder workspaceTabFolder;
	private PluginInfoComposite pluginInfoComposite;
	private ScrolledComposite scrolledPluginInfoComposite;
	private ScrolledComposite scrolledPluginConfigurationComposite;
	private FooterButtonsComposite footerButtonsComposite;
	private PluginsTableComposite pluginsTableComposite;

	public NewTaskDetails(WorkspaceTabFolder workspaceTabFolder) {
		super(workspaceTabFolder, SWT.CLOSE | SWT.H_SCROLL | SWT.V_SCROLL);

		this.workspaceTabFolder=workspaceTabFolder;
		this.setLayout(new GridLayout(3, false));

		createContents();

		//TODO: Should look into a better way of doing this (maybe a feature request for Eclipse RAP)
		this.addListener(SWT.Resize, new Listener() {
			private static final long serialVersionUID = -8815015925218184274L;

			public void handleEvent(Event e) {
				if (!pluginsTableComposite.getPluginsTableViewer().getSelection().isEmpty()) {
					int pluginsTableCompositeSizeX = pluginsTableComposite.getSize().x;
					int pluginInfoCompositeSizeX = pluginInfoComposite.getSize().x;
					int pluginConfigurationUiSizeX = scrolledPluginConfigurationComposite.getContent().getSize().x;

					if (NewTaskDetails.this.getSize().x > pluginsTableCompositeSizeX+pluginInfoCompositeSizeX+pluginConfigurationUiSizeX) {
						if (((GridData)scrolledPluginInfoComposite.getLayoutData()).grabExcessHorizontalSpace) {
							((GridData)scrolledPluginInfoComposite.getLayoutData()).grabExcessHorizontalSpace=false;
						}
					} else {
						if (!((GridData)scrolledPluginInfoComposite.getLayoutData()).grabExcessHorizontalSpace) {
							((GridData)scrolledPluginInfoComposite.getLayoutData()).grabExcessHorizontalSpace=true;
						}
					}
					NewTaskDetails.this.layout();
				}
			}
		});
	}

	private void createContents() {
		pluginsTableComposite = new PluginsTableComposite(this);
		
		scrolledPluginInfoComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledPluginInfoComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		pluginInfoComposite = new PluginInfoComposite(scrolledPluginInfoComposite);
		scrolledPluginInfoComposite.setContent(pluginInfoComposite);
		pluginInfoComposite.setSize(pluginInfoComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		scrolledPluginConfigurationComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledPluginConfigurationComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		footerButtonsComposite = new FooterButtonsComposite(this);
		footerButtonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		((GridData) footerButtonsComposite.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
	}

	public PluginInfoComposite getPluginInfoComposite() {
		return pluginInfoComposite;
	}

	public ScrolledComposite getScrolledPluginConfigurationComposite() {
		return scrolledPluginConfigurationComposite;
	}

	public FooterButtonsComposite getFooterButtonsComposite() {
		return footerButtonsComposite;
	}

	public PluginsTableComposite getPluginsTableComposite() {
		return pluginsTableComposite;
	}

	public WorkspaceTabFolder getWorkspaceTabFolder() {
		return workspaceTabFolder;
	}

}
