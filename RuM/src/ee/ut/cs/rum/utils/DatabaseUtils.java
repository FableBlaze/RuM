package ee.ut.cs.rum.utils;

import java.sql.SQLException;

import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;

public final class DatabaseUtils {
	
	public static SimpleJDBCConnectionPool createDatabaseConnectionPool() {
		SimpleJDBCConnectionPool databaseConnectionPool = null;
		try {
			//TODO: Check implications of initialConnections and maxConnections
			databaseConnectionPool = new SimpleJDBCConnectionPool("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/RuM", "postgres", "postgres",2,5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return databaseConnectionPool;
	}
}
