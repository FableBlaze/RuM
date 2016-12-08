package ee.ut.cs.rum.files.internal.ui.overview.filestable;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import ee.ut.cs.rum.database.domain.UserFile;

public class FilesTableFilter extends ViewerFilter {
	private static final long serialVersionUID = 726056486674117432L;
	
	private String searchString;

	public void setSearchText(String s) {
		this.searchString = s.toLowerCase();
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		UserFile userFile = (UserFile) element;
		if (userFile.getOriginalFilename().toLowerCase().contains(searchString)) {
			return true;
		}
		
		return false;
	}
}
