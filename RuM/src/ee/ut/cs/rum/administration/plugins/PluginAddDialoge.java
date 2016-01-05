package ee.ut.cs.rum.administration.plugins;

import java.sql.SQLException;
import java.sql.Timestamp;
import com.vaadin.data.Item;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeEvent;
import com.vaadin.data.util.sqlcontainer.query.QueryDelegate.RowIdChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class PluginAddDialoge extends Window {
	private static final long serialVersionUID = 1L;

	public PluginAddDialoge(SQLContainer pluginsTableContainer) {
		super("Add new plugin");

		VerticalLayout subContent = new VerticalLayout();
		subContent.setMargin(true);

		//TODO: Hide if no file is uploaded, fill based on upload, make read-only
		TextField fileNameField = new TextField("File name");
		subContent.addComponent(fileNameField);
		TextField fileLocationField = new TextField("File location");
		subContent.addComponent(fileLocationField);
		TextField uploadedField = new TextField("Uploaded");
		subContent.addComponent(uploadedField);
		TextField uploaderField = new TextField("Uploader");
		subContent.addComponent(uploaderField);
		TextField createdField = new TextField("Created");
		subContent.addComponent(createdField);
		TextField creatorField = new TextField("Creator");
		subContent.addComponent(creatorField);
		TextField versionField = new TextField("Version");
		subContent.addComponent(versionField);

		Button pluginAddButton = new Button("Add uploaded plugin");
		pluginAddButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("unchecked")
			public void buttonClick(ClickEvent event) {
				try {
					//TODO: There must be a more decent way to do this
					Object itemTmpId = pluginsTableContainer.addItem();
					Item tmpItem = pluginsTableContainer.getItem(itemTmpId);
					tmpItem.getItemProperty("file_name").setValue("filename");
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



		//TODO: Implement Receiver, page 223
		PluginReceiver pluginReceiver = new PluginReceiver(pluginAddButton);
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
