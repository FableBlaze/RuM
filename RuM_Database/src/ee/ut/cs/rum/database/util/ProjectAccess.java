package ee.ut.cs.rum.database.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ee.ut.cs.rum.database.domain.Project;
import ee.ut.cs.rum.database.internal.Activator;

public final class ProjectAccess {
	
	private ProjectAccess() {
	}
	
	public static long getProjectsCountFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select count(p) from Project p";
		Query query = em.createQuery(queryString);
		Long count = (long) query.getSingleResult();
		
		return count;
	}
	
	public static List<Project> getProjectsDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select p from Project p order by p.id";
		TypedQuery<Project> query = em.createQuery(queryString, Project.class);
		List<Project> projects = query.getResultList();
		
		return projects;
	}
	
	public static Project addProjectDataToDb(Project project) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(project);
		em.getTransaction().commit();
		em.close();
		
		return project;
	}
	
	public static Project updateProjectDataInDb(Project project) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(project);
		em.getTransaction().commit();
		em.close();
		
		return project;
	}
	
	public static void removeProjectDataFromDb(Project project) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(project);
		em.getTransaction().commit();
		em.close();
	}
}
