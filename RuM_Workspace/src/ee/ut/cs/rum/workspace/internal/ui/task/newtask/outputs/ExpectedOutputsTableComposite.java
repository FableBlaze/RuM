package ee.ut.cs.rum.workspace.internal.ui.task.newtask.outputs;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.workspace.internal.ui.task.newtask.NewTaskGeneralInfo;

public class ExpectedOutputsTableComposite extends Composite {
	private static final long serialVersionUID = 2750866640093222059L;
	
	private Display display;
	
	private ExpectedOutputsTableViewer expectedOutputsTableViewer;

	public ExpectedOutputsTableComposite(NewTaskGeneralInfo newTaskGeneralInfo) {
		super(newTaskGeneralInfo, SWT.NONE);
		
		this.display=Display.getCurrent();
		
		this.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, true));
		this.setLayout(new GridLayout(1, false));
		
		Label expectedOutputsLabel = new Label(this, SWT.NONE);
		expectedOutputsLabel.setText("Expected outputs:");
		expectedOutputsLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.expectedOutputsTableViewer = new ExpectedOutputsTableViewer(this);
		expectedOutputsTableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	public void notifyOutputsTableOfPluginSelect(List<UserFile> outputFiles) {
		display.asyncExec(new Runnable() {
			public void run() {
				expectedOutputsTableViewer.add(outputFiles.toArray());				
			}
		});
		
	}

	public void notifyOutputsTableOfPluginDeselect(List<UserFile> outputFiles) {
		display.asyncExec(new Runnable() {
			public void run() {
				expectedOutputsTableViewer.remove(outputFiles.toArray());
			}
		});
	}
	
	public void clearOutputsTable() {
		display.asyncExec(new Runnable() {
			public void run() {
				expectedOutputsTableViewer.getTable().removeAll();
			}
		});
	}
}
