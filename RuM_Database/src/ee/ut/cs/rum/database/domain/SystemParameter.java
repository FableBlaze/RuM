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
import javax.persistence.UniqueConstraint;

import ee.ut.cs.rum.database.domain.interfaces.RumUpdatableEntity;

@Entity
@Table(name="system_parameter", uniqueConstraints = {@UniqueConstraint(columnNames = { "name" }) })
public class SystemParameter implements RumUpdatableEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "value")
	private String value;
	
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
		return "SystemParameter [id=" + id + ", name=" + name + ", description=" + description + ", value=" + value
				+ ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", lastModifiedBy=" + lastModifiedBy
				+ ", lastModifiedAt=" + lastModifiedAt + "]";
	}
}