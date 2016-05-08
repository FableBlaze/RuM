package ee.ut.cs.rum.workspace.internal.ui.overview;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.workspace.internal.ui.project.dialog.NewProjectDialog;
import ee.ut.cs.rum.workspace.ui.WorkspaceUI;

public class ProjectsOverview extends Composite {
	private static final long serialVersionUID = -2991325315513334549L;

	private WorkspaceUI workspaceUI;
	private ProjectsTableViewer projectsTableViewer;

	public ProjectsOverview(WorkspaceUI workspaceUI) {
		super(workspaceUI, SWT.NONE);
		this.workspaceUI=workspaceUI;

		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		ExpandBar bar = new ExpandBar (this, SWT.V_SCROLL);
		bar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Composite statisticsComposite = new Composite (bar, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
		layout.verticalSpacing = 10;
		statisticsComposite.setLayout(layout);
		
		Label label = new Label (statisticsComposite, SWT.NONE);
		label.setText("Number of projects:");
		label = new Label (statisticsComposite, SWT.NONE);
		label.setText("TODO");
		label = new Label (statisticsComposite, SWT.NONE);
		label.setText("Number of tasks:");
		label = new Label (statisticsComposite, SWT.NONE);
		label.setText("TODO");
		
		ExpandItem item0 = new ExpandItem (bar, SWT.NONE, 0);

		item0.setText("Statistics");
		item0.setHeight(statisticsComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item0.setControl(statisticsComposite);



		this.projectsTableViewer = new ProjectsTableViewer(this, bar);
		
		ExpandItem item1 = new ExpandItem (bar, SWT.NONE, 1);
		item1.setText("Projects");
		item1.setHeight(projectsTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		item1.setControl(projectsTableViewer.getTable());
		
		item1.setExpanded(true);

		
		Button addProjectDialogueButton = new Button(this, SWT.PUSH);
		addProjectDialogueButton.setText("Create a new project");
		addProjectDialogueButton.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false));

		addProjectDialogueButton.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 5383804225331390829L;

			public void handleEvent(Event arg0) {
				NewProjectDialog newProjectDialog = new NewProjectDialog(Display.getCurrent().getActiveShell(), workspaceUI);
				newProjectDialog.open();
			}
		});
	}

	public ProjectsTableViewer getProjectsTableViewer() {
		return projectsTableViewer;
	}

	public WorkspaceUI getWorkspaceUI() {
		return workspaceUI;
	}
}
