package ee.ut.cs.rum.workspace.internal.ui.overview.projectstable;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.util.ProjectAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.workspace.internal.ui.overview.WorkspaceOverview;


public class ProjectsTableViewer extends TableViewer implements RumUpdatableView {
	private static final long serialVersionUID = -4856474442900733174L;

	private Display display;

	private List<Project> projects;

	public ProjectsTableViewer(WorkspaceOverview workspaceOverview, ProjectsTableComposite projectsTableComposite, RumController rumController) {
		super(projectsTableComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		this.display=Display.getCurrent();
		rumController.registerView(this, ControllerEntityType.PROJECT);

		createColumns(this);

		final Table table = this.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		//Listening to Table dispose because dispose is not called on TableViewer
		table.addDisposeListener(new DisposeListener() {
			private static final long serialVersionUID = 7634726052580474495L;

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				rumController.unregisterView(ProjectsTableViewer.this, ControllerEntityType.PROJECT);
			}
		});

		table.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -86178746354770036L;

			public void handleEvent(Event e) {
				workspaceOverview.getWorkspaceUI().getWorkspaceHeader().getProjectSelectorCombo().select(table.getSelectionIndex()+1);
				workspaceOverview.getWorkspaceUI().getWorkspaceHeader().getProjectSelectorCombo().updateSelectedProjectDetails();
			}
		});

		this.setContentProvider(new ArrayContentProvider());
		projects = ProjectAccess.getProjectsDataFromDb();
		this.setInput(projects);
	}

	private static void createColumns(final TableViewer viewer) {
		String[] titles = { "Name", "New updates", "Last Updated", "Type"};
		int[] bounds = { 200, 100, 125, 125 };

		TableViewerColumn nameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -6627398148604308908L;

			@Override
			public String getText(Object element) {
				Project project = (Project) element;
				return project.getName();
			}
		});

		TableViewerColumn descriptionColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		descriptionColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 3978700864805271863L;

			@Override
			public String getText(Object element) {
				return "TODO";
			}
		});

		TableViewerColumn createdByColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		createdByColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -8975942909693559289L;

			@Override
			public String getText(Object element) {
				return "TODO";
			}
		});

		TableViewerColumn createdAtColumn = createTableViewerColumn(titles[3], bounds[3], viewer);
		createdAtColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -4095576663886113023L;

			@Override
			public String getText(Object element) {
				return "TODO";
			}
		});
	}

	private static TableViewerColumn createTableViewerColumn(String title, int bound, final TableViewer viewer) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		return viewerColumn;
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof Project) {
			Project project=(Project)updatedEntity;
			int projectIndex;
			switch (updateType) {
			//Both project list and viewer must be updated as updates in one are not reflected automatically to other
			case CREATE:
				projects.add(project);
				display.asyncExec(new Runnable() {
					public void run() {
						ProjectsTableViewer.this.add(project);
					}
				});
				break;
			case MODIFIY:
				projectIndex = findProjectIndex(project);
				if (projectIndex != -1) {
					projects.set(projectIndex, project);
					display.asyncExec(new Runnable() {
						public void run() {
							ProjectsTableViewer.this.replace(project, projectIndex);
						}
					});
				}
				break;
			case DELETE:
				projectIndex = findProjectIndex(project);
				if (projectIndex != -1) {
					synchronized(this){
						display.asyncExec(new Runnable() {
							public void run() {
								ProjectsTableViewer.this.remove(projects.get(projectIndex));
								projects.remove(projectIndex);
							}
						});
					}
				}
				break;
			default:
				break;
			}
		}
	}

	private int findProjectIndex(Project project) {
		for (int i = 0; i < this.projects.size(); i++) {
			if (this.projects.get(i).getId()==project.getId()) {
				return i;
			}
		}
		return -1;
	}
}
