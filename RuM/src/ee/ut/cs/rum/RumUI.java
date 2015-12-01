package ee.ut.cs.rum;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

import ee.ut.cs.rum.administration.plugins.PluginsAdministrationView;
import ee.ut.cs.rum.utils.DatabaseUtils;
import ee.ut.cs.rum.utils.NavigationUtils;

@SuppressWarnings("serial")
@Theme("rum")
@Push
@PreserveOnRefresh
public class RumUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = RumUI.class)
	public static class Servlet extends VaadinServlet {
	}
	
	private Panel content;
	private Navigator mainNavigator;

	@Override
	protected void init(VaadinRequest request) {
		
		//TODO: Handle database connection error more gracefully
		SimpleJDBCConnectionPool databaseConnectionPool = DatabaseUtils.createDatabaseConnectionPool();
		
		content = new Panel();
		this.setContent(content);
		
		mainNavigator = NavigationUtils.createMainNavigator(getUI(), content);
		
		//TODO: Logic to find which page to display initially
		mainNavigator.navigateTo(PluginsAdministrationView.NAME);
	}

}