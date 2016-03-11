package ee.ut.cs.rum.administration.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import ee.ut.cs.rum.administration.internal.Activator;
import ee.ut.cs.rum.database.domain.SystemParameter;

public final class SystemParameterValue {
	
	//TODO: Should provide non internal persistence methods trough database bundle
	public static String getSystemParameterValue(String name) {
		SystemParameter systemParameter = null;
		String systemParameterValue = null;
		
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select sp from SystemParameter sp where sp.name = '" + name + "'");
		try {
			systemParameter = (SystemParameter) query.getSingleResult();
			systemParameterValue = systemParameter.getValue();
		} catch (Exception e) {
			Activator.getLogger().info("Failed querying systemparameter with name: " + name);
		}
		return systemParameterValue;
	}
}
