package ee.ut.cs.rum.files.internal.ui.overview.filestable;

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
import org.eclipse.swt.layout.GridData;
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
import ee.ut.cs.rum.files.internal.ui.FileDownloadButton;
import ee.ut.cs.rum.files.ui.FilesManagementUI;
import ee.ut.cs.rum.interfaces.RumUpdatableView;

public class FilesTableViewer extends TableViewer implements RumUpdatableView {
	private static final long serialVersionUID = 3799035998776154121L;

	private RumController rumController;
	private Display display;

	private List<UserFile> userFiles;

	public FilesTableViewer(FilesTableComposite filesTableComposite, FilesManagementUI filesManagementUI, RumController rumController) {
		super(filesTableComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		this.rumController=rumController;
		this.display=Display.getCurrent();
		rumController.registerView(this, ControllerEntityType.USER_FILE);

		createColumns(this, filesManagementUI);

		final Table table = this.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		//Listening to Table dispose because dispose is not called on TableViewer
		table.addDisposeListener(new DisposeListener() {
			private static final long serialVersionUID = 7634726052580474495L;

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				rumController.unregisterView(FilesTableViewer.this, ControllerEntityType.USER_FILE);
			}
		});
		
		this.setContentProvider(new ArrayContentProvider());
		this.userFiles=UserFileAccess.getUserFilesDataFromDb();
		this.setInput(userFiles);
	}

	private void createColumns(final TableViewer viewer, FilesManagementUI filesManagementUI) {
		String[] titles = { "Id", "Name", "Location", "Created by", "Created at", "Modified by", "Modified at", "Details", "Download"};
		int[] bounds = { 50, 200, 400, 125, 125, 125, 125, 80 , 80 };

		TableViewerColumn idColumn = createTableViewerColumn(titles[0], bounds[0], viewer);
		idColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 5566492295862322452L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return userFile.getId().toString();
			}
		});

		TableViewerColumn originalFileNameColumn = createTableViewerColumn(titles[1], bounds[1], viewer);
		originalFileNameColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -6605721442094893416L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return userFile.getOriginalFilename();
			}
		});
		
		TableViewerColumn fileLocationColumn = createTableViewerColumn(titles[2], bounds[2], viewer);
		fileLocationColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = -6605721442094893416L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return userFile.getFileLocation();
			}
		});

		TableViewerColumn createdByColumn = createTableViewerColumn(titles[3], bounds[3], viewer);
		createdByColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 6391085102757626181L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return userFile.getCreatedBy();
			}
		});

		TableViewerColumn createdAtColumn = createTableViewerColumn(titles[4], bounds[4], viewer);
		createdAtColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 510489586227788453L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return new SimpleDateFormat("dd-MM-yyyy").format(userFile.getCreatedAt());
			}
		});

		TableViewerColumn lastModifiedByColumn = createTableViewerColumn(titles[5], bounds[5], viewer);
		lastModifiedByColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 8797885823572868896L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return userFile.getLastModifiedBy();
			}
		});

		TableViewerColumn lastModifiedAtColumn = createTableViewerColumn(titles[6], bounds[6], viewer);
		lastModifiedAtColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 810828415600352131L;

			@Override
			public String getText(Object element) {
				UserFile userFile = (UserFile) element;
				return new SimpleDateFormat("dd-MM-yyyy").format(userFile.getLastModifiedAt());
			}
		});

		TableViewerColumn detailsButtonColumn = createTableViewerColumn(titles[7], bounds[7], viewer);
		detailsButtonColumn.setLabelProvider(new ColumnLabelProvider() {
			private static final long serialVersionUID = 4559441071410857663L;

			@Override
			public void update(ViewerCell cell) {
				TableItem item = (TableItem) cell.getItem();
				UserFile userFile = (UserFile) cell.getElement();
				
				FileDetailsButton fileDetailsButton = new FileDetailsButton((Composite) cell.getViewerRow().getControl(), userFile, filesManagementUI, rumController);

				item.addDisposeListener(new DisposeListener() {
					private static final long serialVersionUID = -927877657358384078L;

					@Override
					public void widgetDisposed(DisposeEvent arg0) {
						//TODO: Check why dispose() keeps the button in the UI
						fileDetailsButton.setVisible(false);
						fileDetailsButton.dispose();
					}
				});

				TableEditor editor = new TableEditor(item.getParent());
				editor.grabHorizontal  = true;
				editor.grabVertical = true;
				editor.setEditor(fileDetailsButton , item, cell.getColumnIndex());
				editor.layout();
			}
		});
		
		TableViewerColumn downloadButtonColumn = createTableViewerColumn(titles[8], bounds[8], viewer);
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
		if (updatedEntity instanceof UserFile) {
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
