package ee.ut.cs.rum.utils;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

import ee.ut.cs.rum.administration.plugins.PluginsAdministrationView;

public final class NavigationUtils {
	
	public static Navigator createMainNavigator(UI ui, SingleComponentContainer singleComponentContainer) {
		
		Navigator mainNavigator = new Navigator(ui, singleComponentContainer);
		mainNavigator.addView(PluginsAdministrationView.NAME, new PluginsAdministrationView());
		
		return mainNavigator;
	}
}
