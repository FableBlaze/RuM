package ee.ut.cs.rum.workspaces.internal.ui.task.newtask;

import java.util.Date;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.database.domain.Plugin;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.workspaces.internal.util.TasksData;

public class FooterButtonsComposite extends Composite {
	private static final long serialVersionUID = 688156596045927568L;
	
	private Button startButton;

	public FooterButtonsComposite(Composite content, NewTaskDetails newTaskDetails) {
		super(content, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		this.setLayout(new GridLayout(2, false));
		
		startButton = new Button(this, SWT.PUSH);
		startButton.setText("Start");
		startButton.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, true));
		startButton.setEnabled(false);
		
		startButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 5694975289507094763L;

			public void widgetSelected(SelectionEvent event) {
				//Activator.getLogger().info(newTaskDialogShell.getRumPluginConfiguration().getConfiguration().toString());
				Task task = new Task();
				IStructuredSelection selection = (IStructuredSelection) newTaskDetails.getPluginsTableComposite().getPluginsTableViewer().getSelection();
				
				Plugin selectedPlugin = (Plugin) selection.getFirstElement();
				task.setName("TODO");
				task.setStatus("TODO");
				task.setPluginId(selectedPlugin.getId());
				task.setDescription("TODO");
				task.setCreatedBy("TODO");
				task.setCreatedAt(new Date());
				task.setWorkspaceId(newTaskDetails.getWorkspaceTabFolder().getWorkspace().getId());
				TasksData.addTaskDataToDb(task, newTaskDetails);
				
				//TODO: Close tab after task is added
				//newTaskDetails.close();
			}
		});
	}
	
	public Button getStartButton() {
		return startButton;
	}
}
