package ee.ut.cs.rum.plugins.configuration.internal.ui.dialog;

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

public class ObjectInputDialog extends Dialog {
	private static final long serialVersionUID = 6650172134399735836L;
	
	private Shell shell;

	public ObjectInputDialog(Shell activeShell) {
		super(activeShell, SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER | SWT.RESIZE);
	}
	
	public String open() {
		shell = new Shell(this.getParent(), getStyle());
		shell.setText("Add object");
		createContents();
		shell.pack();
		shell.setLocation (100, 100);
		shell.setMinimumSize(shell.computeSize(500, SWT.DEFAULT));
		shell.open();
		return null;
	}

	private void createContents() {
		shell.setLayout(new GridLayout());
		Label l = new Label(shell, SWT.NONE);
		l.setText("TODO");
		l.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createButtonsComposite();
	}
	
	private void createButtonsComposite() {
		Composite buttonsComposite  = new Composite(shell, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(2, false));
		buttonsComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Button okButton = new Button(buttonsComposite, SWT.PUSH);
		okButton.setText("OK");
		okButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true));
		okButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 8685716146386181669L;
			
			public void widgetSelected(SelectionEvent event) {
				//TODO
			}
		});
		
		Button cancel = new Button(buttonsComposite, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, false, true));
		cancel.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -758572263013004367L;

			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});
	}
}
