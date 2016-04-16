package ee.ut.cs.rum.database.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.internal.Activator;

public final class TaskAccess {
	
	private TaskAccess() {
	}
	
	public static List<Task> getTasksDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select t from Task t order by t.id");
		@SuppressWarnings("unchecked")
		List<Task> tasks = query.getResultList();
		
		return tasks;
	}
	
	public static List<Task> getWorkspaceTasksDataFromDb(Long workspaceId) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select t from Task t where t.workspaceId = " + workspaceId + " order by t.id");
		@SuppressWarnings("unchecked")
		List<Task> tasks = query.getResultList();
		
		return tasks;
	}
	
	public static Task getTaskDataFromDb(Long taskId) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.find(Task.class, taskId);
		Task task = em.find(Task.class, taskId);
		
		return task;
	}
}
