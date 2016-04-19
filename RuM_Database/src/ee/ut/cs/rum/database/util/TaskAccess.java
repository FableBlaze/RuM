package ee.ut.cs.rum.database.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.internal.Activator;

public final class TaskAccess {
	
	private TaskAccess() {
	}
	
	//TODO: Add overview of all tasks to the UI
	public static List<Task> getTasksDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select t from Task t order by t.id";
		TypedQuery<Task> query = em.createQuery(queryString, Task.class);
		List<Task> tasks = query.getResultList();
		
		return tasks;
	}
	
	public static List<Task> getWorkspaceTasksDataFromDb(Long workspaceId) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select t from Task t where t.workspaceId = " + workspaceId + " order by t.id";
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
}
