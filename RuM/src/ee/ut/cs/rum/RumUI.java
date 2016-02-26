package ee.ut.cs.rum;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import ee.ut.cs.rum.plugins.ui.PluginsManagementUI;

public class RumUI extends AbstractEntryPoint {
	private static final long serialVersionUID = 1282027955721012064L;

//	private String buttonText = "+1";

	public void createContents( Composite parent ) {
		
//		Button button = new Button(parent, SWT.PUSH);
//		button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
//		button.setText(buttonText);
//
//		final Label label = new Label(parent, SWT.NONE);
//		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
//		label.setText("No event yet.");
//
//		button.addListener(SWT.Selection, new Listener() {
//			private static final long serialVersionUID = 5362888762173659283L;
//			int counter = 0;
//
//			@Override
//			public void handleEvent(Event arg0) {
//				label.setText("Clicked" + " [" + ++counter + "]");
//				Activator.getLogger().info(button.getText() + " was pressed, counter is " + counter);
//
//				Query query = em.createQuery("Select p from Plugin p order by p.id");
//				List<Plugin> plugins = query.getResultList();
//
//				plugins.get(0).setDescription("blah-blah-blah" + counter);
//				em.getTransaction().begin();
//				em.persist(plugins.get(0));
//				em.getTransaction().commit();
//				em.close();
//			}
//		});

		PluginsManagementUI.createPluginsManagementUI(parent);

		Activator.getLogger().info("Someone opened ee.ut.cs.rum.RumUI");
	}

//	public void setButtonText(String buttonText) {
//		this.buttonText = buttonText;
//	}
}
