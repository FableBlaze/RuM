package ee.ut.cs.rum.scheduler.internal.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.scheduler.internal.Activator;

public final class TasksData {
	private TasksData() {
	}
	
	public static Task updateTaskStatusInDb(Long taskId, TaskStatus taskStatus) {
		Task task = TaskAccess.getTaskDataFromDb(taskId);
		task.setStatus(taskStatus);
		
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(task);
		em.getTransaction().commit();
		em.close();
		
		return task;
	}
}
