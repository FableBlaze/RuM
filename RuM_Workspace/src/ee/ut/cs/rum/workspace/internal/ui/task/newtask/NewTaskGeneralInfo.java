package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewTaskGeneralInfo extends Composite {
	private static final long serialVersionUID = 5067024324443453774L;
	
	private Text taskNameText;
	private Text taskDescriptionText;

	public NewTaskGeneralInfo(NewTaskDetailsContainer newTaskDetailsContainer) {
		super(newTaskDetailsContainer, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(3, false));
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Task name:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskNameText = new Text(this, SWT.BORDER);
		taskNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		Label outputFilesTable = new Label(this, SWT.NONE);
		outputFilesTable.setText("Expected task outputs (TODO)");
		outputFilesTable.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		((GridData) outputFilesTable.getLayoutData()).verticalSpan=2;
		
		label = new Label(this, SWT.NONE);
		label.setText("General info:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));
		taskDescriptionText = new Text(this, SWT.BORDER);
		taskDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		Label subTaskGraphComposite = new Label(this, SWT.NONE);
		subTaskGraphComposite.setText("Sub-task graph (TODO)");
		subTaskGraphComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		((GridData) subTaskGraphComposite.getLayoutData()).horizontalSpan=((GridLayout) this.getLayout()).numColumns;
	}
	
	public String getNewTaskName() {
		return taskNameText.getText();
	}
	
	public String getNewTaskDescription() {
		return taskDescriptionText.getText();
	}
}
