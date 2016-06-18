package ee.ut.cs.rum.workspace.internal.ui.project.taskstable;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import ee.ut.cs.rum.database.domain.Task;

public class TasksTableFilter extends ViewerFilter {
	private static final long serialVersionUID = 1304108502753370476L;
	
	private String searchString;

	public void setSearchText(String s) {
		this.searchString = s.toLowerCase();
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Task task = (Task) element;
		if (task.getName().toLowerCase().contains(searchString)) {
			return true;
		}
		if (task.getDescription().toLowerCase().contains(searchString)) {
			return true;
		}

		return false;
	}

}
