package ee.ut.cs.rum.utils;

import java.sql.SQLException;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

import ee.ut.cs.rum.RumUI;

public final class DatabaseUtils {
	
	public static SimpleJDBCConnectionPool createDatabaseConnectionPool() {
		SimpleJDBCConnectionPool databaseConnectionPool = null;
		try {
			//TODO: Check implications of initialConnections and maxConnections
			//TODO: Driver reload fails after server restart if browser is not force-reloaded
			databaseConnectionPool = new SimpleJDBCConnectionPool("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/RuM", "postgres", "postgres",2,5);
		} catch (SQLException e) {
			//TODO: Handle database connection error more gracefully
			e.printStackTrace();
		}
		
		return databaseConnectionPool;
	}
	
	public static SQLContainer createPluginsTableContainer() {
		SQLContainer container = null;
		TableQuery tq = new TableQuery("plugins", RumUI.databaseConnectionPool);
		tq.setVersionColumn("id");
		try {
			container = new SQLContainer(tq);
		} catch (SQLException e) {
			//TODO: Handle container creation error more gracefully
			e.printStackTrace();
		}
		return container;
	}
}
