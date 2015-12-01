package ee.ut.cs.rum.administration.plugins;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class PluginsAdministrationView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "pluginsAdministration";

	public PluginsAdministrationView() {
		this.addComponent(new Table("List of installed plugins"));
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
