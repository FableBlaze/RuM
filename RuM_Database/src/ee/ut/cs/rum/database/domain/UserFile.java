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

import ee.ut.cs.rum.database.domain.interfaces.RumUpdatableEntity;

@Entity
@Table(name="user_file")
public class UserFile implements RumUpdatableEntity {
	//Named UserFile to avoid name conflict with File
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@JoinColumn(name = "project_fk")
	private Project project;
	@JoinColumn(name = "task_fk")
	private Task task;
	@JoinColumn(name = "sub_task_fk")
	private SubTask subTask;
	@JoinColumn(name = "plugin_fk")
	private Plugin plugin;
	
	@Column(name = "original_filename")
	private String originalFilename;
	@Column(name = "file_location")
	private String fileLocation;
	
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Column(name = "last_modified_by")
	private String lastModifiedBy;
	@Column(name = "last_modified_at") //TODO: Implement modifying functionality 
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedAt;
	
	@OneToMany( targetEntity=UserFileType.class, cascade=CascadeType.PERSIST, mappedBy="userFile" )
	private List<UserFileType> userFileTypes;
	
	public Long getId() {
		return id;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
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
	public Plugin getPlugin() {
		return plugin;
	}
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
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
		return "UserFile [id=" + id + ", project=" + project + ", task=" + task + ", subTask=" + subTask + ", plugin="
				+ plugin + ", originalFilename=" + originalFilename + ", fileLocation=" + fileLocation + ", createdBy="
				+ createdBy + ", createdAt=" + createdAt + ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedAt="
				+ lastModifiedAt + ", userFileTypes=" + userFileTypes + "]";
	}
}
