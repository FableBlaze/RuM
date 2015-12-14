package ee.ut.cs.rum.administration.plugins;

import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PluginAddSubWindow extends Window {
	private static final long serialVersionUID = 1L;

	public PluginAddSubWindow() {
		super("Add new plugin");
		
		VerticalLayout subContent = new VerticalLayout();
		subContent.setMargin(true);
		
		//TODO: Hide if no file is uploaded, fill based on upload, make read-only
		subContent.addComponent(new TextField("File name"));
		subContent.addComponent(new TextField("File location"));
		subContent.addComponent(new TextField("Uploaded"));
		subContent.addComponent(new TextField("Uploader"));
		subContent.addComponent(new TextField("Created"));
		subContent.addComponent(new TextField("Creator"));
		subContent.addComponent(new TextField("Version"));
		
		//TODO: Implement Receiver, page 223
		subContent.addComponent(new Upload("Upload it here", null));
		
		this.setContent(subContent);
		this.center();
	}
}
