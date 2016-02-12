package ee.ut.cs.rum;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;

public class RumConfiguration implements ApplicationConfiguration {
	public void configure( Application application ) {
		application.addEntryPoint( "/main", RumUI.class, null );
	}
}
