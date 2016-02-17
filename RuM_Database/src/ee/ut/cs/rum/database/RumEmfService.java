package ee.ut.cs.rum.database;

import javax.persistence.EntityManagerFactory;

public interface RumEmfService {
	public EntityManagerFactory getEmf(String token);
}
