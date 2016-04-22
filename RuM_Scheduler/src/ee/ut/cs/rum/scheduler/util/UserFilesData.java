package ee.ut.cs.rum.scheduler.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.scheduler.internal.Activator;

public final class UserFilesData {
	private UserFilesData() {
	}
	
	public static UserFile addUserFileDataToDb(UserFile userFile) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(userFile);
		em.getTransaction().commit();
		em.close();
		
		Activator.getLogger().info("Added userFile: " + userFile.toString());
		
		return userFile;
	}
}
