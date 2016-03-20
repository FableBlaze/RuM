package ee.ut.cs.rum.database.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import ee.ut.cs.rum.database.domain.Workspace;
import ee.ut.cs.rum.database.internal.Activator;

public final class WorkspaceAccess {
	
	private WorkspaceAccess() {
	}
	
	public static List<Workspace> getWorkspacesDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select ws from Workspace ws order by ws.id");
		@SuppressWarnings("unchecked")
		List<Workspace> workspaces = query.getResultList();
		
		return workspaces;
	}
}
