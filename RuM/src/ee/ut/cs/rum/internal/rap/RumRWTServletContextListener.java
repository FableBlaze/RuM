package ee.ut.cs.rum.internal.rap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.engine.RWTServletContextListener;

public class RumRWTServletContextListener extends RWTServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
	    servletContext.setAttribute(ApplicationConfiguration.RESOURCE_ROOT_LOCATION, "/tmp");
		super.contextInitialized(event);
	}

}
