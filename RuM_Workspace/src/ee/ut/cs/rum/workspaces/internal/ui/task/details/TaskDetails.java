package ee.ut.cs.rum.workspaces.internal.ui.task.details;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import ee.ut.cs.rum.database.domain.Task;
import ee.ut.cs.rum.database.util.TaskAccess;
import ee.ut.cs.rum.workspaces.internal.ui.workspace.WorkspaceTabFolder;

public class TaskDetails extends ScrolledComposite {
	private static final long serialVersionUID = 5855252537558430818L;
	
	private Long taskId;
	private Composite content;

	public TaskDetails(WorkspaceTabFolder workspaceTabFolder, Long taskId) {
		super(workspaceTabFolder, SWT.CLOSE | SWT.H_SCROLL | SWT.V_SCROLL);
		
		this.content = new Composite(this, SWT.NONE);
		content.setLayout(new GridLayout(2, false));
		this.setContent(content);
		
		Task task = TaskAccess.getTaskDataFromDb(taskId);
		
		createContents(task);
		
		content.setSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private void createContents(Task task) {
		Label label = new Label(content, SWT.BORDER);
		label.setText(task.getName()+ " " +task.getId());
	}
	
	public Long getTaskId() {
		return taskId;
	}
}
