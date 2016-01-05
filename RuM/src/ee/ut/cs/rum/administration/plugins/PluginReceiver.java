package ee.ut.cs.rum.administration.plugins;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.vaadin.ui.Button;
import com.vaadin.ui.Upload.ChangeEvent;
import com.vaadin.ui.Upload.ChangeListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class PluginReceiver implements Receiver, SucceededListener, ChangeListener{
	
	private static final long serialVersionUID = 1L;
	
	private Button pluginAddButton;
	
	//TODO: pluginAddButton added for testing, should be replaced with detailFields and tmpPluginItem
	public PluginReceiver(Button pluginAddButton) {
		this.pluginAddButton=pluginAddButton;
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		// TODO: Implement reading into memory
		return new ByteArrayOutputStream(10240);
	}
	
	//TODO: Fill tmpPluginItem and set detailFields visible, instead of messing with pluginAddButton
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		pluginAddButton.setEnabled(true);
	}

	@Override
	public void filenameChanged(ChangeEvent event) {
		pluginAddButton.setEnabled(false);
	}

	
}
