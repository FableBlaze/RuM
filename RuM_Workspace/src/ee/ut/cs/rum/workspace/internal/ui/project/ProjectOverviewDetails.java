package ee.ut.cs.rum.workspace.internal.ui.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.controller.RumController;

public class ProjectOverviewDetails extends Composite {
	private static final long serialVersionUID = 1551715238795937967L;

	public ProjectOverviewDetails(ProjectDetailsContainer taskDetailsContainer, RumController rumController) {
		super(taskDetailsContainer, SWT.NONE);
		
		
		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout(2, false));
		
		Label l = new Label(this, SWT.NONE);
		l.setText("ProjectOverviewDetails");
	}

}
