package ee.ut.cs.rum.workspaces.ui.internal.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import ee.ut.cs.rum.workspaces.internal.Activator;

public class FooterButtonsComposite extends Composite {
	private static final long serialVersionUID = 688156596045927568L;

	public FooterButtonsComposite(NewTaskComposite newTaskComposite) {
		super(newTaskComposite, SWT.NONE);
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		this.setLayout(new GridLayout(2, false));
		
		Button startButton = new Button(this, SWT.PUSH);
		startButton.setText("Start");
		startButton.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, true));
		
		startButton.addSelectionListener(new SelectionAdapter() {
			private static final long serialVersionUID = 5694975289507094763L;

			public void widgetSelected(SelectionEvent event) {
				Activator.getLogger().info(newTaskComposite.getRumPluginConfiguration().getConfiguration().toString());
			}
		});
		
		Button cancelButton = new Button(this, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, true));
	}
}
