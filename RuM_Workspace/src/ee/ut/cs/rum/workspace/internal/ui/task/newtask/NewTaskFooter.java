package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class NewTaskFooter extends Composite {
	private static final long serialVersionUID = -8265567504413682063L;

	public NewTaskFooter(NewTaskDetails newTaskDetails) {
		super(newTaskDetails, SWT.NONE);
		
		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		Button startTaskButton = new Button(this, SWT.PUSH);
		startTaskButton.setText("Start task");
		startTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		startTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -2573553526165067810L;

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button addSubTaskButton = new Button(this, SWT.PUSH);
		addSubTaskButton.setText("Add sub-task");
		addSubTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		addSubTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 5479827915459796014L;

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button removeSubTaskButton = new Button(this, SWT.PUSH);
		removeSubTaskButton.setText("Remove sub-task");
		removeSubTaskButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		removeSubTaskButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 1736754942801222766L;

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
