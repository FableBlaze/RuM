package ee.ut.cs.rum.workspace.internal.ui.overview.projectstable;

import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
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

	public ProjectsTableViewer(ProjectsTableComposite projectsTableComposite, WorkspaceOverview workspaceOverview, RumController rumController) {
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

		this.setContentProvider(new ArrayContentProvider());
		projects = ProjectAccess.getProjectsDataFromDb();
		this.setInput(projects);
	}

	private void createColumns(final TableViewer viewer) {
		String[] titles = { "Name", "New updates", "Last change at", "Type"};
		int[] bounds = { 200, 100, 175, 125 };

		TableViewerColumn nameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -6627398148604308908L;

			@Override
			public String getText(Object element) {
				Project project = (Project) element;
				return project.getName();
			}
		});

		TableViewerColumn newUpdatesColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		newUpdatesColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 3978700864805271863L;

			@Override
			public String getText(Object element) {
				return "TODO";
			}
		});

		TableViewerColumn lastModifiedAtColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		lastModifiedAtColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -8975942909693559289L;

			@Override
			public String getText(Object element) {
				Project project = (Project) element;
				return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(project.getLastModifiedAt());
			}
		});

		TableViewerColumn typeColumn = createTableViewerColumn(titles[3], bounds[3], viewer);
		typeColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -4095576663886113023L;

			@Override
			public String getText(Object element) {
				return "TODO";
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final TableViewer viewer) {
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
			Project project = (Project) updatedEntity;
			int updatedEntityIndex;
			switch (updateType) {
			//Both list and viewer must be updated as updates in one are not reflected automatically to other
			case CREATE:
				projects.add(project);
				display.asyncExec(new Runnable() {
					public void run() {
						ProjectsTableViewer.this.add(project);
					}
				});
				break;
			case MODIFIY:
				updatedEntityIndex = findProjectIndex(project);
				if (updatedEntityIndex != -1) {
					projects.set(updatedEntityIndex, project);
					display.asyncExec(new Runnable() {
						public void run() {
							ProjectsTableViewer.this.replace(project, updatedEntityIndex);
						}
					});
				}
				break;
			case DELETE:
				updatedEntityIndex = findProjectIndex(project);
				if (updatedEntityIndex != -1) {
					synchronized(this){
						display.asyncExec(new Runnable() {
							public void run() {
								ProjectsTableViewer.this.remove(projects.get(updatedEntityIndex));
								projects.remove(updatedEntityIndex);
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
