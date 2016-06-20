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
@Table(name="sub_task")
public class SubTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
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
	@JoinColumn(name = "task_fk")
	private Task task;
	@Column(name = "output_path")
	private String outputPath;
	
	
	
	public Long getId() {
		return id;
	}
	
	
}
