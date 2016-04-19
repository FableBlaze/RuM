package ee.ut.cs.rum.database.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import ee.ut.cs.rum.database.domain.SystemParameter;
import ee.ut.cs.rum.database.domain.enums.SystemParameterName;
import ee.ut.cs.rum.database.internal.Activator;

public final class SystemParameterAccess {
	
	private SystemParameterAccess() {
	}
	
	public static String getSystemParameterValue(SystemParameterName systemParameterName) {
		SystemParameter systemParameter = null;
		String systemParameterValue = null;
		
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select sp from SystemParameter sp where sp.name = '" + systemParameterName.toString() + "'";
		TypedQuery<SystemParameter> query = em.createQuery(queryString, SystemParameter.class);
		
		try {
			systemParameter = query.getSingleResult();
			systemParameterValue = systemParameter.getValue();
		} catch (Exception e) {
			Activator.getLogger().info("Failed querying systemparameter with name: " + systemParameterName);
		}
		return systemParameterValue;
	}
}
