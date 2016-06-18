package ee.ut.cs.rum.workspace.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.database.domain.Project;

public class WorkspaceProjectDetails extends Composite {
	private static final long serialVersionUID = -5990558506997308715L;

	WorkspaceProjectDetails(WorkspaceDetailsContainer workspaceDetailsContainer, Project project) {
		super(workspaceDetailsContainer, SWT.NONE);
		
		this.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new GridLayout());
		
		Label l = new Label(this, SWT.BORDER);
		l.setText(project.toString());
		l.setLayoutData( new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Button b = new Button(this, SWT.BORDER);
		b.setText("Open project");
		b.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 6660200337375128860L;

			public void handleEvent(Event e) {
				workspaceDetailsContainer.getWorkspaceOverview().getWorkspaceUI().getWorkspaceHeader().getProjectSelectorCombo().selectProject(project);
			}
		});
	}

}
