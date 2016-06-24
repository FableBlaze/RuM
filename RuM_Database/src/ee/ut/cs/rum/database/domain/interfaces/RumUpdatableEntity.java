package ee.ut.cs.rum.database.domain.interfaces;

import java.util.Date;

public interface RumUpdatableEntity {
	public String getCreatedBy();
	public void setCreatedBy(String createdBy);
	public Date getCreatedAt();
	public void setCreatedAt(Date createdAt);
	public String getLastModifiedBy();
	public void setLastModifiedBy(String lastModifiedBy);
	public Date getLastModifiedAt();
	public void setLastModifiedAt(Date lastModifiedAt);
}
