package ee.ut.cs.rum;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import ee.ut.cs.rum.database.domain.Plugin;

public class RumUI extends AbstractEntryPoint {
	private static final long serialVersionUID = 1282027955721012064L;

	private String buttonText = "+1";

	public void createContents( Composite parent ) {
		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		button.setText(buttonText);

		final Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
		label.setText("No event yet.");

		button.addListener(SWT.Selection, new Listener() {
			private static final long serialVersionUID = 5362888762173659283L;
			int counter = 0;

			@Override
			public void handleEvent(Event arg0) {
				label.setText("Clicked" + " [" + ++counter + "]");
				Activator.getLogger().info(button.getText() + " was pressed, counter is " + counter);
				
				EntityManagerFactory emf = Activator.getEmf();
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				Plugin plugin = new Plugin();
				plugin.setName("Name" + counter);
				plugin.setDescription("Description" + counter);
				em.persist(plugin);
				em.getTransaction().commit();
				
				Query query = em.createQuery("Select p from Plugin p order by p.id");
				List<Plugin> plugins = query.getResultList();
				
				plugins.get(0).setDescription("blah-blah-blah" + counter);
				em.getTransaction().begin();
				em.persist(plugins.get(0));
				em.getTransaction().commit();
				em.close();
			}
		});
		Activator.getLogger().info("Someone opened ee.ut.cs.rum.RumUI");
	}
	
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
}
