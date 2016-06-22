package ee.ut.cs.rum.database.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.internal.Activator;

public final class SubTaskAccess {
	private SubTaskAccess() {
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
