package ee.ut.cs.rum.database.domain.interfaces;

import java.util.Date;

import ee.ut.cs.rum.database.domain.UserAccount;

public interface RumUpdatableEntity {
	public UserAccount getCreatedBy();
	public void setCreatedBy(UserAccount createdBy);
	public Date getCreatedAt();
	public void setCreatedAt(Date createdAt);
	public UserAccount getLastModifiedBy();
	public void setLastModifiedBy(UserAccount lastModifiedBy);
	public Date getLastModifiedAt();
	public void setLastModifiedAt(Date lastModifiedAt);
}
