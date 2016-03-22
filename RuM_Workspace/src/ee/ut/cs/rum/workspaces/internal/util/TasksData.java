package ee.ut.cs.rum.workspaces.internal.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.workspaces.internal.Activator;

public final class TasksData {
	private TasksData() {
	}
	
	public static void addTaskDataToDb(Task task) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(task);
		em.getTransaction().commit();
		em.close();
	}
}
