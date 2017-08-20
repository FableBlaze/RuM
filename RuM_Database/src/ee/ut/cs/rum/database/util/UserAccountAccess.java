package ee.ut.cs.rum.database.util;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import ee.ut.cs.rum.database.domain.UserAccount;
import ee.ut.cs.rum.database.domain.enums.UserAccountStatus;
import ee.ut.cs.rum.database.internal.Activator;

public class UserAccountAccess {

	private UserAccountAccess() {
	}
	
	public static synchronized UserAccount getSystemUserAccount() {
		//Should only be needed in RuM_Administration bundle - initializeSystemParameters()
		UserAccount userAccount = null;
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		
		String queryString = "Select ua from UserAccount ua where ua.userName = 'system'";
		TypedQuery<UserAccount> query = em.createQuery(queryString, UserAccount.class);
		try {
			userAccount = query.getSingleResult();
		} catch (Exception e) {
			Date date = new Date();
			userAccount = new UserAccount();
			userAccount.setUserName("system");
			userAccount.setStatus(UserAccountStatus.SUSPENDED);
			userAccount.setCreatedAt(date);
			userAccount.setLastModifiedAt(date);
			
			em.getTransaction().begin();
			em.persist(userAccount);
			em.getTransaction().commit();
			em.close();
		}
		return userAccount;
	}
}
