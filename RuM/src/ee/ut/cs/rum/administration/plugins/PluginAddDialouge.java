package ee.ut.cs.rum.administration.plugins;

import java.sql.SQLException;
import java.sql.Timestamp;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;

public class PluginAddDialouge extends Window {
	private static final long serialVersionUID = 1L;

	public PluginAddDialouge(SQLContainer pluginsTableContainer) {
		super("Add new plugin");

		//Create temporary item when creating dialogue so that we can bind it to fields
		//TODO: This is not the way to go. Should create a temporary item and copy it to database on plugin add
		Object tmpItemId = pluginsTableContainer.addItem();
		Item tmpItem = pluginsTableContainer.getItem(tmpItemId);

		//Revert uncommitted temporary item upon dialogue close
		this.addCloseListener(new CloseListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void windowClose(CloseEvent e) {
				try {
					pluginsTableContainer.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		VerticalLayout subContent = new VerticalLayout();
		subContent.setMargin(true);
		
		
		FormLayout pluginFileDetailsLayout = new FormLayout();
		pluginFileDetailsLayout.setCaption("Uploaded plugin details");
		FieldGroup pluginFileDetails = new FieldGroup(tmpItem);
		pluginFileDetailsLayout.addComponent(pluginFileDetails.buildAndBind("File name", "file_name"));
		pluginFileDetailsLayout.addComponent(pluginFileDetails.buildAndBind("File location", "file_location"));
		pluginFileDetailsLayout.addComponent(pluginFileDetails.buildAndBind("Uploaded", "uploaded"));
		pluginFileDetailsLayout.addComponent(pluginFileDetails.buildAndBind("Uploader", "uploader_id"));
		pluginFileDetailsLayout.addComponent(pluginFileDetails.buildAndBind("Created", "created"));
		pluginFileDetailsLayout.addComponent(pluginFileDetails.buildAndBind("Creator", "creator"));
		pluginFileDetailsLayout.addComponent(pluginFileDetails.buildAndBind("Version", "version"));
		pluginFileDetails.setEnabled(false);
		pluginFileDetailsLayout.setVisible(false); //Will be shown once file is uploaded successfully
		subContent.addComponent(pluginFileDetailsLayout);
		
		
		
		
		Button pluginAddButton = new Button("Add uploaded plugin");
		pluginAddButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("unchecked")
			public void buttonClick(ClickEvent event) {
				try {
					tmpItem.getItemProperty("file_location").setValue("filelocation");
					tmpItem.getItemProperty("uploaded").setValue(new Timestamp(System.currentTimeMillis()));
					//TODO: Set uploader_id based on the logged in user
					tmpItem.getItemProperty("uploader_id").setValue(1);
					tmpItem.getItemProperty("created").setValue(new Timestamp(System.currentTimeMillis()));
					tmpItem.getItemProperty("creator").setValue("creatorname");
					tmpItem.getItemProperty("version").setValue("v0.1");
					pluginsTableContainer.commit();
					close();
				} catch(SQLException e) {
					e.printStackTrace();
					try {
						pluginsTableContainer.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		pluginAddButton.setEnabled(false);



		PluginReceiver pluginReceiver = new PluginReceiver(pluginAddButton, pluginFileDetailsLayout, tmpItem);
		Upload upload = new Upload("Upload it here", pluginReceiver);
		upload.addSucceededListener(pluginReceiver);
		upload.addChangeListener(pluginReceiver);
		subContent.addComponent(upload);

		subContent.addComponent(pluginAddButton);



		this.setModal(true);
		this.setContent(subContent);
		this.center();
	}
}
