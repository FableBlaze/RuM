package ee.ut.cs.rum.administration.plugins;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Upload.ChangeEvent;
import com.vaadin.ui.Upload.ChangeListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class PluginReceiver implements Receiver, SucceededListener, ChangeListener{
	
	private static final long serialVersionUID = 1L;
	
	private Button pluginAddButton;
	private FormLayout pluginFileDetailsLayout;
	private Item tmpItem;
	private String filename;
	
	//TODO: Maybe i should use getters instead of passing to constructor?
	public PluginReceiver(Button pluginAddButton, FormLayout pluginFileDetailsLayout, Item tmpItem) {
		this.pluginAddButton=pluginAddButton;
		this.pluginFileDetailsLayout=pluginFileDetailsLayout;
		this.tmpItem=tmpItem;
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		this.filename=filename;
		return new ByteArrayOutputStream(10240);
	}
	
	//TODO: Fill tmpPluginItem
	//TODO: It seems that back-end changes will not be shown in UI unless i commit - this is a problem
	@SuppressWarnings("unchecked")
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		tmpItem.getItemProperty("file_name").setValue(filename);
		pluginAddButton.setEnabled(true);
		pluginFileDetailsLayout.setVisible(true);
	}

	@Override
	public void filenameChanged(ChangeEvent event) {
		pluginAddButton.setEnabled(false);
		pluginFileDetailsLayout.setVisible(false);
	}

	
}
