package ee.ut.cs.rum.plugins.internal.ui.overview.pluginstable;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import ee.ut.cs.rum.database.domain.Plugin;

public class PluginsTableFilter extends ViewerFilter {
	private static final long serialVersionUID = 2513470392701216222L;

	private String searchString;

	public void setSearchText(String s) {
		this.searchString = s.toLowerCase();
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}
		Plugin plugin = (Plugin) element;
		if (plugin.getBundleName().toLowerCase().contains(searchString)) {
			return true;
		}
		if (plugin.getBundleDescription().toLowerCase().contains(searchString)) {
			return true;
		}

		return false;
	}

}
