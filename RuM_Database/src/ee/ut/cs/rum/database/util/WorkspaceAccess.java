package ee.ut.cs.rum.database.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import ee.ut.cs.rum.database.domain.Workspace;
import ee.ut.cs.rum.database.internal.Activator;

public final class WorkspaceAccess {
	
	private WorkspaceAccess() {
	}
	
	public static List<Workspace> getWorkspacesDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select ws from Workspace ws order by ws.id";
		TypedQuery<Workspace> query = em.createQuery(queryString, Workspace.class);
		List<Workspace> workspaces = query.getResultList();
		
		return workspaces;
	}
}
