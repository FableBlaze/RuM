package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class TaskStartFeedbackDialog extends Dialog {
	private static final long serialVersionUID = -8757598978630338189L;

	private Label feedbackTextValue;
	private Label errorTextValue;
	private Button okButton;

	public TaskStartFeedbackDialog(Shell activeShell) {
		super(activeShell, SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER | SWT.RESIZE);
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
		errorTextValue = new Label(shell, SWT.NONE);
		errorTextValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		createButtonsComposite();
	}

	private void createButtonsComposite() {
		Composite buttonsComposite  = new Composite(shell, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout());
		buttonsComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		okButton = new Button(buttonsComposite, SWT.PUSH);
		okButton.setText("OK");
		okButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true));
		okButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -1834525101825011748L;

			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});
	}

	public void setQueingInProgress() {
		feedbackTextValue.setText("Task queing in progress...");
		errorTextValue.setText("");
		okButton.setEnabled(false);
	}

	public void setQueingSuccessful() {
		feedbackTextValue.setText("Task succesfully queued");
		errorTextValue.setText("");
		okButton.setEnabled(true);
	}

	public void setQueingFailure(String message) {
		feedbackTextValue.setText("Task queing failed");
		errorTextValue.setText(message);
		okButton.setEnabled(true);

	}
}
