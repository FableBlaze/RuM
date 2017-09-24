package ee.ut.cs.rum.database.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.internal.Activator;

public final class TaskAccess {
	
	private TaskAccess() {
	}
	
	public static long getTasksCountFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select count(t) from Task t";
		Query query = em.createQuery(queryString);
		Long count = (long) query.getSingleResult();
		
		return count;
	}
	
	public static List<Task> getTasksDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select t from Task t order by t.id";
		TypedQuery<Task> query = em.createQuery(queryString, Task.class);
		List<Task> tasks = query.getResultList();
		
		return tasks;
	}
	
	public static Task getTaskDataFromDb(Long taskId) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		Task task = em.find(Task.class, taskId);
		return task;
	}
	
	public static Task addTaskDataToDb(Task task) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(task);
		em.getTransaction().commit();
		em.close();
		
		return task;
	}

	public static Task updateTaskDataInDb(Task task) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(task);
		em.getTransaction().commit();
		em.close();
		
		return task;
	}

	public static void removeTaskDataFromDb(Task task) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(task);
		em.getTransaction().commit();
		em.close();
	}

	public static List<Task> getProjectTasksDataFromDb(Long projectId) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select t from Task t where t.project.id = " + projectId + " order by t.id";
		TypedQuery<Task> query = em.createQuery(queryString, Task.class);
		List<Task> tasks = query.getResultList();
		
		return tasks;
	}
}
