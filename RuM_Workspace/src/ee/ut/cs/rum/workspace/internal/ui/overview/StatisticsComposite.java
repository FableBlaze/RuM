package ee.ut.cs.rum.workspace.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class StatisticsComposite extends Composite {
	private static final long serialVersionUID = -6946774249575610410L;

	StatisticsComposite(ProjectsOverviewExpandBar projectsOverviewExpandBar) {
		super(projectsOverviewExpandBar, SWT.NONE);
		
		GridLayout layout = new GridLayout(2, false);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		layout.verticalSpacing = 10;
		this.setLayout(layout);
		
		Label label = new Label (this, SWT.NONE);
		label.setText("Number of projects:");
		label = new Label (this, SWT.NONE);
		label.setText("TODO");
		label = new Label (this, SWT.NONE);
		label.setText("Number of tasks:");
		label = new Label (this, SWT.NONE);
		label.setText("TODO");
	}

}
