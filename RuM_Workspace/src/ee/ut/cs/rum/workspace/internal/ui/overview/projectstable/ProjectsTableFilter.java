package ee.ut.cs.rum.workspace.internal.ui.overview.projectstable;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import ee.ut.cs.rum.database.domain.Project;

public class ProjectsTableFilter extends ViewerFilter {
	private static final long serialVersionUID = 3238503525768235493L;
	
	private String searchString;

	public void setSearchText(String s) {
		this.searchString = s.toLowerCase();
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Project project = (Project) element;
		if (project.getName().toLowerCase().contains(searchString)) {
			return true;
		}
		if (project.getDescription().toLowerCase().contains(searchString)) {
			return true;
		}

		return false;
	}

}
