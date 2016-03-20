package ee.ut.cs.rum.workspaces.internal.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ee.ut.cs.rum.database.domain.Workspace;
import ee.ut.cs.rum.database.util.WorkspaceAccess;
import ee.ut.cs.rum.workspaces.internal.Activator;
import ee.ut.cs.rum.workspaces.internal.ui.workspace.WorkspacesOverview;

public class WorkspacesData {
	
	private WorkspacesData() {
	}
	
	public static void addWorkspaceDataToDb(Workspace workspace, WorkspacesOverview workspacesOverview) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(workspace);
		em.getTransaction().commit();
		em.close();
		
		List<Workspace> workspaces = WorkspaceAccess.getWorkspacesDataFromDb();
		workspacesOverview.getWorkspacesTableViewer().setInput(workspaces);
	}
}
