package ee.ut.cs.rum.database.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.internal.Activator;

public final class SubTaskAccess {
	private SubTaskAccess() {
	}
	
	public static long getSubTasksCountFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select count(st) from SubTask st";
		Query query = em.createQuery(queryString);
		Long count = (long) query.getSingleResult();
		
		return count;
	}
	
	public static SubTask getSubTaskDataFromDb(Long subTaskId) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		SubTask subTask = em.find(SubTask.class, subTaskId);
		return subTask;
	}
	
	public static List<SubTask> getTaskSubtasksDataFromDb(Long taskId) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select st from SubTask st where st.task.id = " + taskId + " order by st.id";
		TypedQuery<SubTask> query = em.createQuery(queryString, SubTask.class);
		List<SubTask> tasks = query.getResultList();
		
		return tasks;
	}
	
	public static SubTask addSubTaskDataToDb(SubTask subTask) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(subTask);
		em.getTransaction().commit();
		em.close();
		
		return subTask;
	}

	public static SubTask updateSubTaskDataInDb(SubTask subTask) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(subTask);
		em.getTransaction().commit();
		em.close();
		
		return subTask;
	}

	public static void removeSubTaskDataFromDb(SubTask subTask) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(subTask);
		em.getTransaction().commit();
		em.close();
	}
}
