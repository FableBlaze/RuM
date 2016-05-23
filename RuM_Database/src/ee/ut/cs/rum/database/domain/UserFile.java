package ee.ut.cs.rum.database.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name = "created_by_plugin_id")
	private Long createdByPluginId;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "task_id")
	private Long taskId;
	@Column(name = "project_id")
	private Long projectId;
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
	public Long getCreatedByPluginId() {
		return createdByPluginId;
	}
	public void setCreatedByPluginId(Long createdByPluginId) {
		this.createdByPluginId = createdByPluginId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
				+ ", createdByPluginId=" + createdByPluginId + ", createdAt=" + createdAt + ", taskId=" + taskId
				+ ", projectId=" + projectId + ", fileLocation=" + fileLocation + ", userFileTypes=" + userFileTypes
				+ "]";
	}
	
}
