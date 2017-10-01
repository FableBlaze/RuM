package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.database.domain.Task;

public class NewTaskGeneralInfo extends Composite {
	private static final long serialVersionUID = 5067024324443453774L;
	
	private Task task;
	private Text taskNameText;
	private Text taskDescriptionText;

	public NewTaskGeneralInfo(NewTaskDetailsContainer newTaskDetailsContainer) {
		super(newTaskDetailsContainer, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3, false));
		
		task = newTaskDetailsContainer.getNewTaskComposite().getTask();
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Task name:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskNameText = new Text(this, SWT.BORDER);
		taskNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		taskNameText.setText(task.getName());
		taskNameText.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 4820831828850851112L;
			@Override
			public void focusLost(FocusEvent event) {
				task.setName(taskNameText.getText());
			}
			@Override
			public void focusGained(FocusEvent event) {
			}
		});

		
		Label outputFilesTable = new Label(this, SWT.NONE);
		outputFilesTable.setText("Expected task outputs (TODO)");
		outputFilesTable.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		((GridData) outputFilesTable.getLayoutData()).verticalSpan=2;
		
		label = new Label(this, SWT.NONE);
		label.setText("Task description:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskDescriptionText = new Text(this, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		taskDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		taskDescriptionText.addFocusListener(new FocusListener() {
			private static final long serialVersionUID = 4820831828850851112L;
			@Override
			public void focusLost(FocusEvent event) {
				task.setDescription(taskDescriptionText.getText());
			}
			@Override
			public void focusGained(FocusEvent event) {
			}
		});
		
		Label subTaskGraphComposite = new Label(this, SWT.NONE);
		subTaskGraphComposite.setText("Sub-task graph (TODO)");
		subTaskGraphComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		((GridData) subTaskGraphComposite.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
	}

	public void initializeBasedOnTask(Task baseTask) {
		taskNameText.setText(baseTask.getName());
		task.setName(baseTask.getName());
		taskDescriptionText.setText(baseTask.getDescription());
		task.setDescription(baseTask.getDescription());
	}
}
