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

	public TaskStartFeedbackDialog(Shell activeShell) {
		super(activeShell, SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER | SWT.RESIZE);
	}
	
	public String open() {
		shell = new Shell(getParent(), getStyle());
		createContents();
		shell.pack();
		shell.setLocation (100, 100);
		shell.setMinimumSize(shell.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		shell.open();
		return null;
	}

	private void createContents() {
		shell.setLayout(new GridLayout());
		
		Label label = new Label(shell, SWT.NONE);
		label.setText("Test");
		createButtonsComposite();
	}

	private void createButtonsComposite() {
		Composite buttonsComposite  = new Composite(shell, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout());
		buttonsComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Button okButton = new Button(buttonsComposite, SWT.PUSH);
		okButton.setText("OK");
		okButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true));
		okButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -1834525101825011748L;

			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});
	}

}
