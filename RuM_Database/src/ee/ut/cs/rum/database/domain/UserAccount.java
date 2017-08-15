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

import ee.ut.cs.rum.database.domain.enums.UserAccountStatus;
import ee.ut.cs.rum.database.domain.interfaces.RumUpdatableEntity;

@Entity
@Table(name="user_account")
public class UserAccount implements RumUpdatableEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") //TODO: Set references to user
	private Long id;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "password")
	private UserAccountStatus pasword; //TODO: Encryption
	@Column(name = "status")
	private UserAccountStatus status;
	
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Column(name = "last_modified_by")
	private String lastModifiedBy;
	@Column(name = "last_modified_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedAt;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public UserAccountStatus getPasword() {
		return pasword;
	}
	public void setPasword(UserAccountStatus pasword) {
		this.pasword = pasword;
	}
	public UserAccountStatus getStatus() {
		return status;
	}
	public void setStatus(UserAccountStatus status) {
		this.status = status;
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
	public Long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", pasword=" + pasword + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", lastModifiedBy=" + lastModifiedBy
				+ ", lastModifiedAt=" + lastModifiedAt + "]";
	}
}
