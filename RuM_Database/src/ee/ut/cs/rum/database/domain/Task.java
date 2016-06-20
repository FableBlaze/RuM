package ee.ut.cs.rum.database.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ee.ut.cs.rum.database.domain.enums.TaskStatus;

@Entity
@Table(name="task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "status")
	private TaskStatus status;
	@JoinColumn(name = "plugin_fk")
	private Plugin plugin;
	@Column(name = "configuration_values", columnDefinition = "TEXT")
	private String configurationValues;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@JoinColumn(name = "project_fk")
	private Project project;
	@Column(name = "output_path")
	private String outputPath;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public Plugin getPlugin() {
		return plugin;
	}
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
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
	public Project getProject() {
		return project;
	}
	public void setProjectId(Project project) {
		this.project = project;
	}
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", status=" + status + ", plugin=" + plugin + ", description="
				+ description + ", configurationValues=" + configurationValues + ", createdBy=" + createdBy
				+ ", createdAt=" + createdAt + ", project=" + project + ", outputPath=" + outputPath
				+ "]";
	}
}
