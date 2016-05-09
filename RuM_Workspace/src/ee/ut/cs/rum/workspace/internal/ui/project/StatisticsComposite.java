package ee.ut.cs.rum.workspace.internal.ui.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.Project;

public class StatisticsComposite extends Composite {
	private static final long serialVersionUID = -1951507631009200420L;

	StatisticsComposite(ProjectOverviewExpandBar projectOverviewExpandBar) {
		super(projectOverviewExpandBar, SWT.NONE);
		
		GridLayout layout = new GridLayout(2, false);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		layout.verticalSpacing = 10;
		this.setLayout(layout);
		
		Project project = projectOverviewExpandBar.getProjectOverviewComposite().getProject();
		
		Label label = new Label(this, SWT.NONE);
		label.setText("Project name: ");
		label = new Label(this, SWT.NONE);
		label.setText(project.getName());
		label = new Label(this, SWT.NONE);
		label.setText("Project description: ");
		label.setLayoutData(new GridData(SWT.TOP, SWT.FILL, false, false));
		label = new Label(this, SWT.NONE);
		label.setText(project.getDescription());
		label = new Label (this, SWT.NONE);
		label.setText("Total number of tasks:");
		label = new Label (this, SWT.NONE);
		label.setText("TODO");
	}
}
