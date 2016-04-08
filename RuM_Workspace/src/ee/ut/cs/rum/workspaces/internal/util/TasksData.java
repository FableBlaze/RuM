package ee.ut.cs.rum.workspaces.internal.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.workspaces.internal.Activator;
import ee.ut.cs.rum.workspaces.internal.ui.task.newtask.NewTaskDetails;

public final class TasksData {
	private TasksData() {
	}
	
	public static Task addTaskDataToDb(Task task, NewTaskDetails newTaskDetails) {
		EntityManagerFactory emf = Activator.getEmf();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(task);
		em.getTransaction().commit();
		em.close();
		
		List<Task> workspaceTasks = TaskAccess.getWorkspaceTasksDataFromDb(newTaskDetails.getWorkspaceTabFolder().getWorkspace().getId());
		newTaskDetails.getWorkspaceTabFolder().getWorkspaceDetailsTabContents().getTasksTableViewer().setInput(workspaceTasks);
		
		return task;
	}
}
