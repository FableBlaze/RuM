package ee.ut.cs.rum.workspace.internal.ui.overview.projectstable;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.workspace.internal.Activator;

public class ProjectSelectionChangedListener implements ISelectionChangedListener {

	public ProjectSelectionChangedListener() {
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		Project selectedProject=null;
		
		if (event!=null) {
			IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			selectedProject = (Project) selection.getFirstElement();			
		}

		if (selectedProject!=null) {
			Activator.getLogger().info(selectedProject.toString());
		} else {
			Activator.getLogger().info("Project deselected");
		}
	}
}
