package ee.ut.cs.rum.database.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="user_file")
public class UserFile {
	//Named UserFile to avoid name conflict with File
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "original_filename")
	private String originalFilename;

	@Column(name = "created_by_user_id")
	private String createdByUserId;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@JoinColumn(name = "task_fk")
	private Task task;
	@JoinColumn(name = "sub_task_fk")
	private SubTask subTask;
	@JoinColumn(name = "project_fk")
	private Project project;
	@JoinColumn(name = "plugin_fk")
	private Plugin plugin;
	
	@Column(name = "file_location")
	private String fileLocation;

	@OneToMany( targetEntity=UserFileType.class, cascade=CascadeType.PERSIST, mappedBy="userFile" )
	private List<UserFileType> userFileTypes;
	
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getCreatedByUserId() {
		return createdByUserId;
	}
	public void setCreatedByUserId(String createdByUserId) {
		this.createdByUserId = createdByUserId;
	}
	public Plugin getPlugin() {
		return plugin;
	}
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public SubTask getSubTask() {
		return subTask;
	}
	public void setSubTask(SubTask subTask) {
		this.subTask = subTask;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public Long getId() {
		return id;
	}
	public List<UserFileType> getUserFileTypes() {
		return userFileTypes;
	}
	public void setUserFileTypes(List<UserFileType> userFileTypes) {
		for (UserFileType userFileType : userFileTypes) {
			userFileType.setUserFile(this);
		}
		this.userFileTypes = userFileTypes;
	}
	@Override
	public String toString() {
		return "UserFile [id=" + id + ", originalFilename=" + originalFilename + ", createdByUserId=" + createdByUserId
				+ ", createdAt=" + createdAt + ", task=" + task + ", subTask=" + subTask + ", project=" + project
				+ ", plugin=" + plugin + ", fileLocation=" + fileLocation + ", userFileTypes=" + userFileTypes + "]";
	}
}
