package ee.ut.cs.rum.administration.plugins;

import java.sql.SQLException;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import ee.ut.cs.rum.RumUI;
import ee.ut.cs.rum.utils.DatabaseUtils;

public class PluginsAdministrationView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "pluginsAdministration";

	public PluginsAdministrationView() {
		VerticalLayout pluginsAdministrationLayout = new VerticalLayout();
		Table pluginsTable = new Table("List of installed plugins", DatabaseUtils.createPluginsTableContainer());
		pluginsTable.setImmediate(true);
		
		//TODO: Implement query to get total count
		pluginsAdministrationLayout.addComponent(new Label("Total plugins:"));
		//TODO: Implement query to get in use count (cant be done before implementing task management)
		pluginsAdministrationLayout.addComponent(new Label("In use plugins:"));
		pluginsAdministrationLayout.addComponent(pluginsTable);
		
		this.addComponent(pluginsAdministrationLayout);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
