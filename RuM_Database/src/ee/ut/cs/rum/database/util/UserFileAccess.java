package ee.ut.cs.rum.database.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.internal.Activator;

public final class UserFileAccess {
	
	private UserFileAccess() {
	}
	
	public static List<UserFile> getUserFilesDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select uf from UserFile uf order by uf.id";
		TypedQuery<UserFile> query = em.createQuery(queryString, UserFile.class);
		List<UserFile> userFiles = query.getResultList();
		
		return userFiles;
	}
	
	public static List<UserFile> getProjectUserFilesDataFromDb(Long projectId) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select uf from UserFile uf where uf.project.id = " + projectId + " order by uf.id";
		TypedQuery<UserFile> query = em.createQuery(queryString, UserFile.class);
		List<UserFile> userFiles = query.getResultList();
		
		return userFiles;
	}
	
	public static List<UserFile> getProjectUserFilesDataFromDb(Long projectId, String[] inputTypes) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String inputTypesString = "("; 
		for (int i = 0; i < inputTypes.length; i++) {
			if (i==inputTypes.length-1) {
				inputTypesString += "'" + inputTypes[i] + "'";
			} else {
				inputTypesString += "'" + inputTypes[i]+"',";
			}
		}
		inputTypesString += ")";
		
		String queryString = "Select distinct uft.userFile from UserFileType uft where uft.typeName in " + inputTypesString +
				" and uft.userFile.project.id = " + projectId;
		TypedQuery<UserFile> query = em.createQuery(queryString, UserFile.class);
		List<UserFile> userProjectFiles = query.getResultList();
		
		return userProjectFiles;
	}
	
	public static List<UserFile> getTaskUserFilesDataFromDb(Long taskId) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select uf from UserFile uf where uf.task.id = " + taskId + " order by uf.id";
		TypedQuery<UserFile> query = em.createQuery(queryString, UserFile.class);
		List<UserFile> userFiles = query.getResultList();
		
		return userFiles;
	}
	
	public static UserFile getUserFileDataFromDb(String fileLocation) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select uf from UserFile uf where uf.fileLocation = '" + fileLocation + "'";
		TypedQuery<UserFile> query = em.createQuery(queryString, UserFile.class);
		
		UserFile userFile = query.getSingleResult();
		return userFile;
	}
	
	//TODO: Add a way to see details of a file
	public static UserFile getUserFileDataFromDb(Long userFileId) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		UserFile userFile = em.find(UserFile.class, userFileId);
		return userFile;
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

	public static UserFile updateUserFileDataInDb(UserFile userFile) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(userFile);
		em.getTransaction().commit();
		em.close();
		
		return userFile;
	}

	public static UserFile removeUserFileDataFromDb(UserFile userFile) {
		//TODO: Remove from filesystem
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(userFile);
		em.getTransaction().commit();
		em.close();
		
		return userFile;
	}

}
