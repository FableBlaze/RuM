package ee.ut.cs.rum.database.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Column(name = "uploaded_by")
	private String uploadedBy;
	@Column(name = "uploaded_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadedAt;
	
	@Column(name = "created_by_plugin_id")
	private Long createdByPluginId;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "file_location")
	private String fileLocation;

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Date getUploadedAt() {
		return uploadedAt;
	}

	public void setUploadedAt(Date uploadedAt) {
		this.uploadedAt = uploadedAt;
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

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "UserFile [id=" + id + ", originalFilename=" + originalFilename + ", uploadedBy=" + uploadedBy
				+ ", uploadedAt=" + uploadedAt + ", createdByPluginId=" + createdByPluginId + ", createdAt=" + createdAt
				+ ", fileLocation=" + fileLocation + "]";
	}
}
