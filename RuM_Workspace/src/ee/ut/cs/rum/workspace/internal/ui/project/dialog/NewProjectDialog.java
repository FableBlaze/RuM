package ee.ut.cs.rum.workspace.internal.ui.project.dialog;

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
import org.eclipse.swt.widgets.Text;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.util.UserAccountAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;

public class NewProjectDialog extends Dialog {
	private static final long serialVersionUID = -9152678513520036179L;
	
	private RumController rumController;
	private Shell shell;
	
	private Text nameValue;
	private Text descriptionValue;
	private Label feedbackTextValue;
	
	private Button okButton;
	
	public NewProjectDialog(Shell activeShell, RumController rumController) {
		super(activeShell, SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER | SWT.RESIZE);
		this.rumController=rumController;
	}
	
	public String open() {
		shell = new Shell(getParent(), getStyle());
		shell.setText("Add project");
		createContents();
		shell.pack();
		shell.setLocation (100, 100);
		shell.setMinimumSize(shell.computeSize(500, 300));
		shell.open();
		return null;
	}

	private void createContents() {
		shell.setLayout(new GridLayout());
		
		createInputsComposite();
		feedbackTextValue = new Label(shell, SWT.NONE);
		feedbackTextValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		createButtonsComposite();
	}

	private void createInputsComposite() {
		Composite inputsComposite  = new Composite(shell, SWT.NONE);
		inputsComposite.setLayout(new GridLayout(2, false));
		inputsComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label nameLabel = new Label(inputsComposite, SWT.NONE);
		nameLabel.setText("Project name:");
		nameValue = new Text(inputsComposite, SWT.BORDER);
		nameValue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label descriptionNameLabel = new Label(inputsComposite, SWT.NONE);
		descriptionNameLabel.setText("Project description:");
		descriptionNameLabel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		descriptionValue = new Text(inputsComposite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		descriptionValue.setLayoutData(new GridData(GridData.FILL_BOTH));
	}
	
	private void createButtonsComposite() {
		Composite buttonsComposite  = new Composite(shell, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(2, false));
		buttonsComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		okButton = new Button(buttonsComposite, SWT.PUSH);
		okButton.setText("OK");
		okButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true));
		okButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -4306176546808600070L;
			
			public void widgetSelected(SelectionEvent event) {
				if (nameValue.getText().isEmpty() || descriptionValue.getText().isEmpty()) {
					feedbackTextValue.setText("Name and description must be filled");
				} else {
					Project project = new Project();
					project.setName(nameValue.getText());
					project.setDescription(descriptionValue.getText());
					rumController.changeData(ControllerUpdateType.CREATE, ControllerEntityType.PROJECT, project, UserAccountAccess.getGenericUserAccount());
					
					shell.close();
				}
			}
		});
		
		Button cancel = new Button(buttonsComposite, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, false, true));
		cancel.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = -4620143960354064523L;

			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});
	}
}
