package ee.ut.cs.rum.administration.internal.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import ee.ut.cs.rum.administration.internal.Activator;
import ee.ut.cs.rum.database.domain.SystemParameter;
import ee.ut.cs.rum.database.domain.enums.SystemParameterName;

public final class SystemParametersData {
	
	private SystemParametersData() {
	}
	
	public static void initializeSystemParameters() {
		SystemParameter systemParameter = new SystemParameter();
		systemParameter.setName(SystemParameterName.PLUGIN_PATH.toString());
		systemParameter.setDescription("Location of plugin jars");
		SystemParametersData.addSystemParameterDataToDb(systemParameter);
		
		systemParameter = new SystemParameter();
		systemParameter.setName(SystemParameterName.UPLOAD_FILE_PATH.toString());
		systemParameter.setDescription("Location of user uploaded files");
		SystemParametersData.addSystemParameterDataToDb(systemParameter);
		
		systemParameter = new SystemParameter();
		systemParameter.setName(SystemParameterName.TASK_RESULTS_ROOT.toString());
		systemParameter.setDescription("Location of task output folders");
		SystemParametersData.addSystemParameterDataToDb(systemParameter);
	}
	
	public static List<SystemParameter> getSystemParametersDataFromDb() {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select sp from SystemParameter sp order by sp.id";
		TypedQuery<SystemParameter> query = em.createQuery(queryString, SystemParameter.class);
		List<SystemParameter> systemParameters = query.getResultList();
		
		return systemParameters;
	}
	
	public static SystemParameter getSystemParameterDataFromDb(SystemParameterName systemParameterName) {
		SystemParameter systemParameter = null;
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select sp from SystemParameter sp where sp.name = '" + systemParameterName.toString() + "'";
		TypedQuery<SystemParameter> query = em.createQuery(queryString, SystemParameter.class);
		
		try {
			systemParameter = query.getSingleResult();
		} catch (Exception e) {
			Activator.getLogger().info("Failed querying systemparameter with name: " + systemParameterName);
		}
		return systemParameter;
	}
	
	public static boolean updateParameterValue(SystemParameterName systemParameterName, String newValue) {
		boolean setValueSuccess = false;
		SystemParameter systemParameter = getSystemParameterDataFromDb(systemParameterName);
		if (systemParameter!=null) {
			systemParameter.setValue(newValue);
			EntityManagerFactory emf = Activator.getEmf();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.merge(systemParameter);
			try {
				em.getTransaction().commit();
				setValueSuccess = true;
				Activator.getLogger().info("Modified systemParameter: " + systemParameter.toString());
			} catch (Exception e) {
				Activator.getLogger().info("Failed modifiying systemParameter: " + systemParameter.toString());
			} finally {
				em.close();
			}
		} else {
			Activator.getLogger().info("Can not get system parameter with name: " + systemParameterName);
		}
		return setValueSuccess;
	}
	
	private static void addSystemParameterDataToDb(SystemParameter systemParameter) {
		SystemParameter existingSystemParameter = getSystemParameterDataFromDb(SystemParameterName.valueOf(systemParameter.getName()));
		
		if (existingSystemParameter==null) {
			EntityManagerFactory emf = Activator.getEmf();
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(systemParameter);
			try {
				em.getTransaction().commit();
				Activator.getLogger().info("Added systemParameter: " + systemParameter.toString());
			} catch (Exception e) {
				Activator.getLogger().info("Failed commiting systemParameter: " + systemParameter.toString());
			} finally {
				em.close();
			}
		} else {
			Activator.getLogger().info("Skipped adding existing systemParameter: " + systemParameter.toString());
		}
	}
}
