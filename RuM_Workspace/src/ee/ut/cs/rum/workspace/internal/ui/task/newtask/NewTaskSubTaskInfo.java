package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.workspace.internal.ui.task.PluginInfoComposite;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.pluginstable.PluginsTableComposite;

public class NewTaskSubTaskInfo extends Composite {
	private static final long serialVersionUID = -9081862727975335668L;
	
	private NewTaskDetailsContainer newTaskDetailsContainer;
	
	private Text subTaskNameText;
	private Text subTaskDescriptionText;
	
	private PluginInfoComposite pluginInfoComposite;
	private ScrolledComposite scrolledPluginConfigurationComposite;

	public NewTaskSubTaskInfo(NewTaskDetailsContainer newTaskDetailsContainer, RumController rumController) {
		super(newTaskDetailsContainer, SWT.NONE);
		
		this.newTaskDetailsContainer=newTaskDetailsContainer;
		
		this.setLayout(new GridLayout(4, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		PluginsTableComposite pluginsTableComposite = new PluginsTableComposite(this, rumController);
		((GridData) pluginsTableComposite.getLayoutData()).verticalSpan=3;
		
		pluginInfoComposite = new PluginInfoComposite(this);
		pluginInfoComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		((GridData) pluginInfoComposite.getLayoutData()).verticalSpan=3;
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Sub-task name:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		subTaskNameText = new Text(this, SWT.BORDER);
		subTaskNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		subTaskNameText.addModifyListener(new ModifyListener() {
			private static final long serialVersionUID = -4275986926694404712L;

			@Override
			public void modifyText(ModifyEvent event) {
				Table table = newTaskDetailsContainer.getNewTaskComposite().getDetailsSideBar().getSubTaskTableViewer().getTable();
				table.getItem(table.getSelectionIndex()).setText(0, subTaskNameText.getText());
			}
		});
		
		label = new Label(this, SWT.NONE);
		label.setText("Sub-task description:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));
		subTaskDescriptionText = new Text(this, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		subTaskDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		((GridData)subTaskDescriptionText.getLayoutData()).heightHint=75;
		
		scrolledPluginConfigurationComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledPluginConfigurationComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		((GridData) scrolledPluginConfigurationComposite.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns-2;
	}
	
	public NewTaskDetailsContainer getNewTaskDetailsContainer() {
		return newTaskDetailsContainer;
	}
	
	public String getSubTaskName() {
		return subTaskNameText.getText();
	}
	
	public String getSubTaskDescription() {
		return subTaskDescriptionText.getText();
	}
	
	public PluginInfoComposite getPluginInfoComposite() {
		return pluginInfoComposite;
	}
	
	public ScrolledComposite getScrolledPluginConfigurationComposite() {
		return scrolledPluginConfigurationComposite;
	}
}
