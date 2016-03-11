package ee.ut.cs.rum.administration.internal.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import ee.ut.cs.rum.administration.internal.Activator;
import ee.ut.cs.rum.database.domain.SystemParameter;

public final class SystemParametersData {
	
	private SystemParametersData() {
	}
	
	public static void initializeSystemParameters() {
		SystemParameter systemParameter = new SystemParameter();
		systemParameter.setParameterName("plugin_path");
		systemParameter.setParameterDescription("Location of plugin jars");
		SystemParametersData.addsystemParameterDataToDb(systemParameter);
	}
	
	public static SystemParameter getSystemParameterDataFromDb(String parameterName) {
		SystemParameter system_parameter = null;
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select sp from SystemParameter sp where sp.parameterName = '" + parameterName + "'");
		try {
			system_parameter = (SystemParameter) query.getSingleResult();
		} catch (Exception e) {
			Activator.getLogger().info("Failed querying systemparameter with name: " + parameterName);
		}
		return system_parameter;
	}
	
	private static void addsystemParameterDataToDb(SystemParameter systemParameter) {
		SystemParameter existingSystemParameter = getSystemParameterDataFromDb(systemParameter.getParameterName());
		
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
