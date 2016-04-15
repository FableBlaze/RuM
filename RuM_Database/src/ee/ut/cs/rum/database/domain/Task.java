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
@Table(name="task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "status")
	private TaskStatusEnum status;
	@Column(name = "plugin_id")
	private Long pluginId;
	@Column(name = "description")
	private String description;
	@Column(name = "configuration_values", columnDefinition = "TEXT")
	private String configurationValues;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Column(name = "workspace_id")
	private Long workspaceId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TaskStatusEnum getStatus() {
		return status;
	}
	public void setStatus(TaskStatusEnum status) {
		this.status = status;
	}
	public Long getPluginId() {
		return pluginId;
	}
	public void setPluginId(Long pluginId) {
		this.pluginId = pluginId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getConfigurationValues() {
		return configurationValues;
	}
	public void setConfigurationValues(String configurationValues) {
		this.configurationValues = configurationValues;
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
	public Long getId() {
		return id;
	}
	public Long getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(Long workspaceId) {
		this.workspaceId = workspaceId;
	}
	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", status=" + status + ", pluginId=" + pluginId + ", description="
				+ description + ", configurationValues=" + configurationValues + ", createdBy=" + createdBy + ", createdAt="
				+ createdAt + ", workspaceId=" + workspaceId + "]";
	}
}
