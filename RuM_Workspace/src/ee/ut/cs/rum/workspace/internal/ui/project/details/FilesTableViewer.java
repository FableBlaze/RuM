package ee.ut.cs.rum.workspace.internal.ui.project.details;

import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.util.UserFileAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.workspace.internal.download.FileDownloadButton;

public class FilesTableViewer extends TableViewer implements RumUpdatableView {
	private static final long serialVersionUID = 1485288926212834460L;

	private Display display;

	private ProjectTaskDetails projectTaskDetails;
	private List<UserFile> userFiles;

	public FilesTableViewer(ProjectTaskDetails projectTaskDetails, RumController rumController) {
		super(projectTaskDetails, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		this.display=Display.getCurrent();
		rumController.registerView(this, ControllerEntityType.USER_FILE);

		this.projectTaskDetails=projectTaskDetails;

		createColumns(this);

		final Table table = this.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		//Listening to Table dispose because dispose is not called on TableViewer
		table.addDisposeListener(new DisposeListener() {
			private static final long serialVersionUID = 832776161208430560L;

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				rumController.unregisterView(FilesTableViewer.this, ControllerEntityType.USER_FILE);
			}
		});
		
		this.setContentProvider(new ArrayContentProvider());
		userFiles = UserFileAccess.getTaskUserFilesDataFromDb(projectTaskDetails.getTask().getId());
		this.setInput(userFiles);
	}

	private void createColumns(FilesTableViewer viewer) {
		String[] titles = { "Name", "Sub-task", "Modified at", "Download"};
		int[] bounds = { 200, 150, 125, 80 };
		
		TableViewerColumn nameColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		nameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -8548614225037785582L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return userFile.getOriginalFilename();
			}
		});

		TableViewerColumn subTaskNameColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		subTaskNameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 6912154647126756659L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return userFile.getSubTask().getName();
			}
		});
		
		TableViewerColumn lastModifiedAtColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		lastModifiedAtColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 617292996496741188L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return new SimpleDateFormat("dd-MM-yyyy").format(userFile.getLastModifiedAt());
			}
		});
		
		TableViewerColumn downloadButtonColumn = createTableViewerColumn(titles[3], bounds[3], viewer);
		downloadButtonColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 4559441071410857663L;

			@Override
			public void update(ViewerCell cell) {
				TableItem item = (TableItem) cell.getItem();
				UserFile userFile = (UserFile) cell.getElement();
				
				FileDownloadButton fileDownloadButton = new FileDownloadButton((Composite) cell.getViewerRow().getControl(), userFile);
				
				item.addDisposeListener(new DisposeListener() {
					private static final long serialVersionUID = -927877657358384078L;

					@Override
					public void widgetDisposed(DisposeEvent arg0) {
						//TODO: Check why dispose() keeps the button in the UI
						fileDownloadButton.setVisible(false);
						fileDownloadButton.dispose();
					}
				});

				TableEditor editor = new TableEditor(item.getParent());
				editor.grabHorizontal  = true;
				editor.grabVertical = true;
				editor.setEditor(fileDownloadButton , item, cell.getColumnIndex());
				editor.layout();
			}
		});
		
	}
	
	private TableViewerColumn createTableViewerColumn(String title, int bound, final TableViewer viewer) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		return viewerColumn;
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (updatedEntity instanceof UserFile && ((UserFile)updatedEntity).getTask().getId()==projectTaskDetails.getTask().getId()) {
			UserFile userFile = (UserFile) updatedEntity;
			int updatedEntityIndex;
			switch (updateType) {
			//Both list and viewer must be updated as updates in one are not reflected automatically to other
			case CREATE:
				userFiles.add(userFile);
				display.asyncExec(new Runnable() {
					public void run() {
						FilesTableViewer.this.add(userFile);
					}
				});
				break;
			case MODIFIY:
				updatedEntityIndex = findUserFileIndex(userFile);
				if (updatedEntityIndex != -1) {
					userFiles.set(updatedEntityIndex, userFile);
					display.asyncExec(new Runnable() {
						public void run() {
							FilesTableViewer.this.replace(userFile, updatedEntityIndex);
						}
					});
				}
				break;
			case DELETE:
				updatedEntityIndex = findUserFileIndex(userFile);
				if (updatedEntityIndex != -1) {
					synchronized(this){
						display.asyncExec(new Runnable() {
							public void run() {
								FilesTableViewer.this.remove(userFiles.get(updatedEntityIndex));
								userFiles.remove(updatedEntityIndex);
							}
						});
					}
				}
				break;
			default:
				break;
			}
		}
	}
	
	private int findUserFileIndex(UserFile userFile) {
		for (int i = 0; i < this.userFiles.size(); i++) {
			if (this.userFiles.get(i).getId()==userFile.getId()) {
				return i;
			}
		}
		return -1;
	}
}
