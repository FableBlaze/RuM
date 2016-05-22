package ee.ut.cs.rum.database.util;

import java.util.ArrayList;
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
		
		String queryString = "Select uf from UserFile uf where uf.workspaceId = " + projectId + " order by uf.id";
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
		
		String helperQueryString = "Select uft.userFile from UserFileType uft where uft.typeName in " + inputTypesString;
		TypedQuery<UserFile> helperQuery = em.createQuery(helperQueryString, UserFile.class);
		List<UserFile> userFiles = helperQuery.getResultList();
				
		List<UserFile> userProjectFiles = new ArrayList<UserFile>();
		for (UserFile userFile : userFiles) {
			if (userFile.getWorkspaceId()==projectId) {
				userProjectFiles.add(userFile);
			}
		}
		
		return userProjectFiles;
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

}
