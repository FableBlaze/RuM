package ee.ut.cs.rum.administration.internal.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import ee.ut.cs.rum.administration.internal.Activator;
import ee.ut.cs.rum.database.domain.SystemParameter;

public final class SystemParametersData {
	
	private SystemParametersData() {
	}
	
	public static void initializeSystemParameters() {
		SystemParameter systemParameter = new SystemParameter();
		systemParameter.setName("plugin_path");
		systemParameter.setDescription("Location of plugin jars");
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
	
	public static SystemParameter getSystemParameterDataFromDb(String name) {
		SystemParameter systemParameter = null;
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select sp from SystemParameter sp where sp.name = '" + name + "'";
		TypedQuery<SystemParameter> query = em.createQuery(queryString, SystemParameter.class);
		
		try {
			systemParameter = query.getSingleResult();
		} catch (Exception e) {
			Activator.getLogger().info("Failed querying systemparameter with name: " + name);
		}
		return systemParameter;
	}
	
	public static boolean updateParameterValue(String name, String newValue) {
		boolean setValueSuccess = false;
		SystemParameter systemParameter = getSystemParameterDataFromDb(name);
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
			Activator.getLogger().info("Can not get system parameter with name: " + name);
		}
		return setValueSuccess;
	}
	
	private static void addSystemParameterDataToDb(SystemParameter systemParameter) {
		SystemParameter existingSystemParameter = getSystemParameterDataFromDb(systemParameter.getName());
		
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
