package ee.ut.cs.rum.workspace.internal.ui.task.newtask;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import ee.ut.cs.rum.controller.RumController;
import ee.ut.cs.rum.database.domain.SubTask;
import ee.ut.cs.rum.database.domain.UserFile;
import ee.ut.cs.rum.database.util.UserFileAccess;
import ee.ut.cs.rum.enums.ControllerEntityType;
import ee.ut.cs.rum.enums.ControllerUpdateType;
import ee.ut.cs.rum.interfaces.RumUpdatableView;
import ee.ut.cs.rum.plugins.configuration.ui.PluginConfigurationComposite;
import ee.ut.cs.rum.workspace.internal.Activator;

public class NewTaskDetailsContainer extends Composite implements RumUpdatableView {
	private static final long serialVersionUID = -7982581022298012511L;

	private Display display;
	private RumController rumController;

	private NewTaskComposite newTaskComposite;
	private NewTaskGeneralInfo newTaskGeneralInfo;

	private List<UserFile> userFiles;
	private List<UserFile> tmpUserFiles;
	private List<NewTaskSubTaskInfo> newTaskSubTaskInfoList;

	public NewTaskDetailsContainer(NewTaskComposite newTaskComposite, RumController rumController) {
		super(newTaskComposite, SWT.NONE);

		this.display=Display.getCurrent();
		this.rumController=rumController;
		rumController.registerView(this, ControllerEntityType.USER_FILE);

		this.newTaskComposite=newTaskComposite;
		this.userFiles = UserFileAccess.getProjectUserFilesDataFromDb(newTaskComposite.getTask().getProject().getId());
		this.tmpUserFiles = new TmpUserFileArrayList(this);

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.setLayout(new StackLayout());

		this.newTaskGeneralInfo = new NewTaskGeneralInfo(this);

		this.newTaskSubTaskInfoList = new ArrayList<NewTaskSubTaskInfo>();

		((StackLayout)this.getLayout()).topControl = newTaskGeneralInfo;
		this.layout();
	}

	public void showGeneralInfo() {
		((StackLayout)this.getLayout()).topControl = newTaskGeneralInfo;
		this.layout();
		newTaskComposite.getNewTaskFooter().setRemoveSubTaskButtonVisible(false);
	}

	public void showSubTaskInfo(int subTaskIndex) {
		((StackLayout)this.getLayout()).topControl = newTaskSubTaskInfoList.get(subTaskIndex);
		this.layout();
		newTaskComposite.getNewTaskFooter().setRemoveSubTaskButtonVisible(true);
	}

	public NewTaskComposite getNewTaskComposite() {
		return newTaskComposite;
	}

	public NewTaskGeneralInfo getNewTaskGeneralInfo() {
		return newTaskGeneralInfo;
	}

	public List<NewTaskSubTaskInfo> getNewTaskSubTaskInfoList() {
		return newTaskSubTaskInfoList;
	}

	public List<UserFile> getUserFiles() {
		return userFiles;
	}

	public List<UserFile> getInitialTaskUserFiles(NewTaskSubTaskInfo newTaskSubTaskInfo) {
		List<UserFile> initialTaskUserFiles = new ArrayList<UserFile>();
		for (int i = 0; i < newTaskSubTaskInfoList.indexOf(newTaskSubTaskInfo); i++) {
			PluginConfigurationComposite pluginConfigurationComposite =((PluginConfigurationComposite)newTaskSubTaskInfoList.get(i).getScrolledPluginConfigurationComposite().getContent());
			if (pluginConfigurationComposite!=null) {
				initialTaskUserFiles.addAll(pluginConfigurationComposite.getOutputUserFiles());
			}
		}
		return initialTaskUserFiles;
	}

	public List<UserFile> getTmpUserFiles() {
		return tmpUserFiles;
	}

	public void notifyTaskOfPluginSelect(List<UserFile> outputFiles, NewTaskSubTaskInfo newTaskSubTaskInfo) {
		display.syncExec(new Runnable() {
			public void run() {
				if (newTaskSubTaskInfoList.indexOf(newTaskSubTaskInfo) != -1) {
					for (int i = newTaskSubTaskInfoList.indexOf(newTaskSubTaskInfo)+1; i < newTaskSubTaskInfoList.size(); i++) {
						PluginConfigurationComposite pluginConfigurationComposite =((PluginConfigurationComposite)newTaskSubTaskInfoList.get(i).getScrolledPluginConfigurationComposite().getContent());
						if (pluginConfigurationComposite!=null && pluginConfigurationComposite.isDisposed()==false) {
							pluginConfigurationComposite.notifySubTaskOfPluginSelect(outputFiles);
						}	
					} 
				} else {
					Activator.getLogger().info("newTaskSubTaskInfo not found");
				}
			}
		});		
	}

	public void notifyTaskOfPluginDeselect(List<UserFile> outputFiles, NewTaskSubTaskInfo newTaskSubTaskInfo) {
		display.syncExec(new Runnable() {
			public void run() {
				if (newTaskSubTaskInfoList.indexOf(newTaskSubTaskInfo) != -1) {
					for (int i = newTaskSubTaskInfoList.indexOf(newTaskSubTaskInfo)+1; i < newTaskSubTaskInfoList.size(); i++) {
						PluginConfigurationComposite pluginConfigurationComposite =((PluginConfigurationComposite)newTaskSubTaskInfoList.get(i).getScrolledPluginConfigurationComposite().getContent());
						if (pluginConfigurationComposite!=null && pluginConfigurationComposite.isDisposed()==false) {
							pluginConfigurationComposite.notifySubTaskOfPluginDeselect(outputFiles);
						}	
					}
				} else {
					Activator.getLogger().info("newTaskSubTaskInfo not found");
				}
			}
		});
	}

	public void notifyTaskOfSubTaskNameChange(SubTask subTask) {
		display.syncExec(new Runnable() {
			public void run() {
				List<UserFile> outputFiles = null;
				int newTaskSubTaskInfoIndex = -1;
				for (int i = 0; i < newTaskSubTaskInfoList.size(); i++) {
					if (newTaskSubTaskInfoList.get(i).getSubTask() == subTask) {
						outputFiles = ((PluginConfigurationComposite)newTaskSubTaskInfoList.get(i).getScrolledPluginConfigurationComposite().getContent()).getOutputUserFiles();
						newTaskSubTaskInfoIndex = i;
						break;
					}
				}
				if (newTaskSubTaskInfoIndex != -1) {
					for (int i = newTaskSubTaskInfoIndex+1; i < newTaskSubTaskInfoList.size(); i++) {
						PluginConfigurationComposite pluginConfigurationComposite =((PluginConfigurationComposite)newTaskSubTaskInfoList.get(i).getScrolledPluginConfigurationComposite().getContent());
						if (pluginConfigurationComposite!=null && pluginConfigurationComposite.isDisposed()==false) {
							pluginConfigurationComposite.notifySubTaskOfSubTaskNameChange(outputFiles);
						}	
					}					
				} else {
					Activator.getLogger().info("newTaskSubTaskInfo not found");
				}
			}
		});
	}

	public void notifyTaskOfTmpFileUpload(UserFile tmpUserFile) {
		display.syncExec(new Runnable() {
			public void run() {							
				for (NewTaskSubTaskInfo newTaskSubTaskInfo : newTaskSubTaskInfoList) {
					PluginConfigurationComposite pluginConfigurationComposite = (PluginConfigurationComposite)newTaskSubTaskInfo.getScrolledPluginConfigurationComposite().getContent();
					if (pluginConfigurationComposite != null && pluginConfigurationComposite.isDisposed()==false) {
						pluginConfigurationComposite.notifySubTaskOfTmpFileUpload(tmpUserFile);						
					}
				}
			}
		});
	}

	@Override
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity) {
		if (this.userFiles != null && updatedEntity instanceof UserFile) {
			UserFile userFile = (UserFile) updatedEntity;
			if (userFile.getProject()!=null && userFile.getProject().getId()==newTaskComposite.getProjectTabFolder().getProject().getId() && !userFile.getUserFileTypes().isEmpty()) {
				switch (updateType) {
				case CREATE:
					userFiles.add(userFile);
					display.asyncExec(new Runnable() {
						public void run() {							
							for (NewTaskSubTaskInfo newTaskSubTaskInfo : newTaskSubTaskInfoList) {
								PluginConfigurationComposite pluginConfigurationComposite = (PluginConfigurationComposite)newTaskSubTaskInfo.getScrolledPluginConfigurationComposite().getContent();
								if (pluginConfigurationComposite != null && pluginConfigurationComposite.isDisposed()==false) {
									pluginConfigurationComposite.addUserFile(userFile);									
								}
							}
						}
					});
					break;
				case MODIFIY:
					for (int i = 0; i < userFiles.size(); i++) {
						if (userFile.getId()==userFiles.get(i).getId()) {
							userFiles.set(i, userFile);
							display.asyncExec(new Runnable() {
								public void run() {	
									for (NewTaskSubTaskInfo newTaskSubTaskInfo : newTaskSubTaskInfoList) {
										PluginConfigurationComposite pluginConfigurationComposite = (PluginConfigurationComposite)newTaskSubTaskInfo.getScrolledPluginConfigurationComposite().getContent();
										if (pluginConfigurationComposite != null && pluginConfigurationComposite.isDisposed()==false) {
											pluginConfigurationComposite.modifyUserFile(userFile);											
										}
									}
								}
							});
							break;
						}
					}
					break;
				case DELETE:
					userFiles.remove(userFile);
					display.asyncExec(new Runnable() {
						public void run() {	
							for (NewTaskSubTaskInfo newTaskSubTaskInfo : newTaskSubTaskInfoList) {
								PluginConfigurationComposite pluginConfigurationComposite = (PluginConfigurationComposite)newTaskSubTaskInfo.getScrolledPluginConfigurationComposite().getContent();
								if (pluginConfigurationComposite != null && pluginConfigurationComposite.isDisposed()==false) {
									pluginConfigurationComposite.removeUserFile(userFile);
								}
							}
						}
					});
					break;
				default:
					break;
				}
			}
		}
		Activator.getLogger().info("Update recived (TODO)");
	}

	@Override
	public void dispose() {
		rumController.unregisterView(this, ControllerEntityType.PROJECT);
		super.dispose();
	}

}
