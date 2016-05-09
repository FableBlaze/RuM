package ee.ut.cs.rum.workspace.internal.ui.overview;

import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.util.ProjectAccess;


public class ProjectsTableViewer extends TableViewer {
	private static final long serialVersionUID = -4856474442900733174L;

	public ProjectsTableViewer(WorkspaceOverviewExpandBar workspaceOverviewExpandBar, WorkspaceOverview workspaceOverview) {
		super(workspaceOverviewExpandBar, SWT.H_SCROLL | SWT.V_SCROLL);

		createColumns(this);

		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		table.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = -86178746354770036L;

			public void handleEvent(Event e) {
				workspaceOverview.getWorkspaceUI().getWorkspaceHeader().getProjectSelectorCombo().select(table.getSelectionIndex()+1);
				workspaceOverview.getWorkspaceUI().getWorkspaceHeader().getProjectSelectorCombo().updateSelectedProjectDetails();
			}
		});

		this.setContentProvider(new ArrayContentProvider());
		this.setInput(ProjectAccess.getProjectsDataFromDb());
	}

	private static void createColumns(final TableViewer viewer) {
		String[] titles = { "Name", "Description", "Created by", "Created at"};
		int[] bounds = { 200, 700, 125, 125 };

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
				Project project = (Project) element;
				return project.getDescription();
			}
		});
		
		TableViewerColumn createdByColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		createdByColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -8975942909693559289L;

			@Override
			public String getText(Object element) {
				Project project = (Project) element;
				return project.getCreatedBy();
			}
		});
		
		TableViewerColumn createdAtColumn = createTableViewerColumn(titles[3], bounds[3], viewer);
		createdAtColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -4095576663886113023L;

			@Override
			public String getText(Object element) {
				Project project = (Project) element;
				return new SimpleDateFormat("dd-MM-yyyy").format(project.getCreatedAt());
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
}
