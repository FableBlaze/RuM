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

import ee.ut.cs.rum.database.domain.enums.UserAccountStatus;
import ee.ut.cs.rum.database.domain.interfaces.RumUpdatableEntity;

@Entity
@Table(name="user_account")
public class UserAccount implements RumUpdatableEntity {
	//Named UserAccount because user is reserved in postgres
	//Can be used later as a basis for implementing actual user accounts
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") //TODO: Set references to user
	private Long id;
	@Column(name = "user_name", unique=true, nullable=false)
	private String userName;
	@Column(name = "status")
	private UserAccountStatus status;
	
	@JoinColumn(name = "created_by_fk")
	private UserAccount createdBy;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@JoinColumn(name = "last_modified_by_fk")
	private UserAccount lastModifiedBy;
	@Column(name = "last_modified_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedAt;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public UserAccountStatus getStatus() {
		return status;
	}
	public void setStatus(UserAccountStatus status) {
		this.status = status;
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
	public Long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", lastModifiedBy=" + lastModifiedBy
				+ ", lastModifiedAt=" + lastModifiedAt + "]";
	}
}
