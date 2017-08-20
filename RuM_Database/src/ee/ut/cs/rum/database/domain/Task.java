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

import ee.ut.cs.rum.database.domain.enums.TaskStatus;
import ee.ut.cs.rum.database.domain.interfaces.RumUpdatableEntity;

@Entity
@Table(name="task")
public class Task implements RumUpdatableEntity {
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
	@OneToMany(mappedBy="task", cascade=CascadeType.PERSIST)
	private List<SubTask> subTasks;
	@JoinColumn(name = "project_fk")
	private Project project;
	@JoinColumn(name = "created_by_fk")
	private UserAccount createdBy;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@JoinColumn(name = "last_modified_by_fk")
	private UserAccount lastModifiedBy;
	@Column(name = "last_modified_at") //TODO: Implement modifying functionality 
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedAt;
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public List<SubTask> getSubTasks() {
		return subTasks;
	}
	public void setSubTasks(List<SubTask> subTasks) {
		for (SubTask subTask : subTasks) {
			subTask.setTask(this);
		}
		this.subTasks = subTasks;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public UserAccount getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserAccount createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public UserAccount getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(UserAccount lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", description=" + description + ", status=" + status
				+ ", project=" + project + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", lastModifiedBy="
				+ lastModifiedBy + ", lastModifiedAt=" + lastModifiedAt + "]";
		}
}
