package ee.ut.cs.rum.database.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.internal.Activator;

public final class ProjectAccess {
	
	private ProjectAccess() {
	}
	
	public static List<Project> getProjectssDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select p from Project p order by p.id";
		TypedQuery<Project> query = em.createQuery(queryString, Project.class);
		List<Project> projects = query.getResultList();
		
		return projects;
	}
	
	public static Project addWorkspaceDataToDb(Project projects) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(projects);
		em.getTransaction().commit();
		em.close();
		
		return projects;
	}
}
