package ee.ut.cs.rum.internal;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;

import ee.ut.cs.rum.internal.ui.RumUI;

public class RumConfiguration implements ApplicationConfiguration {
	public void configure( Application application ) {
		application.addEntryPoint( "/main", RumUI.class, null );
	}
}
