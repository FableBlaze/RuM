package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.workspace.internal.ui.project.ProjectTabFolder;
import ee.ut.cs.rum.workspace.internal.ui.task.details.TaskDetailsComposite;

public class NewTaskStartFeedbackDialog extends Dialog {
	private static final long serialVersionUID = -8757598978630338189L;
	
	private RumController rumController;
	private NewTaskComposite newTaskComposite;

	private Label feedbackTextValue;
	private Label errorTextValue;
	private Composite buttonsComposite;
	private Button projectButton;
	private Button detailsButton;
	private Button newTaskButton;
	private Button okButton;

	public NewTaskStartFeedbackDialog(Shell activeShell, RumController rumController, NewTaskComposite newTaskComposite) {
		super(activeShell, SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER | SWT.RESIZE);
		
		this.rumController=rumController;
		this.newTaskComposite = newTaskComposite; 
	}

	public String open() {
		shell = new Shell(getParent(), getStyle());
		createContents();
		shell.pack();
		shell.setLocation (100, 100);
		shell.setMinimumSize(shell.computeSize(500, SWT.DEFAULT));
		shell.open();
		return null;
	}

	private void createContents() {
		shell.setLayout(new GridLayout());

		feedbackTextValue = new Label(shell, SWT.NONE);
		feedbackTextValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		feedbackTextValue.setText("Task queing in progress...");
		errorTextValue = new Label(shell, SWT.NONE);
		errorTextValue.setLayoutData(new GridData(GridData.FILL_BOTH));
		errorTextValue.setText("");
		buttonsComposite = createButtonsComposite();
		buttonsComposite.setEnabled(false);
	}

	private Composite createButtonsComposite() {
		Composite buttonsComposite  = new Composite(shell, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(4, false));
		buttonsComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		projectButton = new Button(buttonsComposite, SWT.PUSH);
		projectButton.setText("Project overwiev");
		projectButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		projectButton.setEnabled(false);
		projectButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -1834525101825011748L;

			public void widgetSelected(SelectionEvent event) {
				ProjectTabFolder projectTabFolder = newTaskComposite.getProjectTabFolder();
				for (CTabItem c : projectTabFolder.getItems()) {
					if (c.getControl().getClass() == NewTaskComposite.class) {
						c.dispose();
					}
				}
				newTaskComposite.getProjectTabFolder().setSelection(0);
				shell.close();
			}
		});

		detailsButton = new Button(buttonsComposite, SWT.PUSH);
		detailsButton.setText("Task details");
		detailsButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		detailsButton.setEnabled(false);
		detailsButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 6754562075280340546L;

			public void widgetSelected(SelectionEvent event) {
				ProjectTabFolder projectTabFolder = newTaskComposite.getProjectTabFolder();
				Long taskId = null;
				for (CTabItem c : projectTabFolder.getItems()) {
					if (c.getControl().getClass() == NewTaskComposite.class) {
						taskId = ((NewTaskComposite)c.getControl()).getTask().getId();
						c.dispose();
					}
				}
				if (taskId!=null) {
					CTabItem cTabItem = new CTabItem (projectTabFolder, SWT.CLOSE);
					cTabItem.setText ("Task " + taskId.toString());
					cTabItem.setControl(new TaskDetailsComposite(projectTabFolder, rumController, taskId));
					projectTabFolder.setSelection(cTabItem);					
				}
				shell.close();
			}
		});
		
		newTaskButton = new Button(buttonsComposite, SWT.PUSH);
		newTaskButton.setText("New task");
		newTaskButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		newTaskButton.setEnabled(false);
		newTaskButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 2862658622963869743L;

			public void widgetSelected(SelectionEvent event) {
				ProjectTabFolder projectTabFolder = newTaskComposite.getProjectTabFolder();
				for (CTabItem c : projectTabFolder.getItems()) {
					if (c.getControl().getClass() == NewTaskComposite.class) {
						c.dispose();
					}
				}
				CTabItem cTabItem = new CTabItem (projectTabFolder, SWT.CLOSE);
				cTabItem.setText ("New task");
				Task task = new Task();
				task.setName("(New task)");
				task.setDescription("");
				task.setProject(projectTabFolder.getProject());
				task.setStatus(TaskStatus.NEW);
				cTabItem.setControl(new NewTaskComposite(projectTabFolder, task, rumController));
				projectTabFolder.setSelection(cTabItem);
				shell.close();
			}
		});
		
		okButton = new Button(buttonsComposite, SWT.PUSH);
		okButton.setText("OK");
		okButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		okButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -1834525101825011748L;

			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});
		return buttonsComposite;
	}

	public void setQueingSuccessful() {
		feedbackTextValue.setText("Task succesfully queued");
		errorTextValue.setText("");
		buttonsComposite.setEnabled(true);
		projectButton.setEnabled(true);
		detailsButton.setEnabled(true);
		newTaskButton.setEnabled(true);
	}

	public void setQueingFailure(String message) {
		feedbackTextValue.setText("Task queing failed");
		errorTextValue.setText(message);
		buttonsComposite.setEnabled(true);

	}
}
