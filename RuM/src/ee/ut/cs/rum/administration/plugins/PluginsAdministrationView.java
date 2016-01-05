package ee.ut.cs.rum.administration.plugins;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ee.ut.cs.rum.utils.DatabaseUtils;

public class PluginsAdministrationView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "pluginsAdministration";

	public PluginsAdministrationView() {
		VerticalLayout pluginsAdministrationLayout = new VerticalLayout();
		
		SQLContainer pluginsTableContainer = DatabaseUtils.createPluginsTableContainer();
		
		Table pluginsTable = new Table("List of installed plugins", pluginsTableContainer);
		pluginsTable.setImmediate(true);
		
		Button openPluginAddDialoge = new Button("Add new plugin...");
		openPluginAddDialoge.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				PluginAddDialouge pluginAddSubWindow = new PluginAddDialouge(pluginsTableContainer);
				UI.getCurrent().addWindow(pluginAddSubWindow);
			}
		});
		
		
		//TODO: Implement query to get total count
		pluginsAdministrationLayout.addComponent(new Label("Total plugins:"));
		//TODO: Implement query to get in use count (cant be done before implementing task management)
		pluginsAdministrationLayout.addComponent(new Label("In use plugins:"));
		pluginsAdministrationLayout.addComponent(pluginsTable);
		pluginsAdministrationLayout.addComponent(openPluginAddDialoge);
		
		this.addComponent(pluginsAdministrationLayout);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
