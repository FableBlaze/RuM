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

import ee.ut.cs.rum.database.domain.interfaces.RumUpdatableEntity;

@Entity
@Table(name="plugin")
public class Plugin implements RumUpdatableEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "bundle_symbolic_name")
	private String bundleSymbolicName;
	@Column(name = "bundle_version")
	private String bundleVersion;
	@Column(name = "bundle_name")
	private String bundleName;
	@Column(name = "bundle_vendor")
	private String bundleVendor;
	@Column(name = "bundle_description")
	private String bundleDescription;
	@Column(name = "bundle_activator")
	private String bundleActivator;
	@Column(name = "bundle_import_package", columnDefinition = "TEXT")
	private String bundleImportPackage;
	
	@Column(name = "plugin_name")
	private String pluginName;
	@Column(name = "plugin_description")
	private String pluginDescription;
	@Column(name = "plugin_info", columnDefinition = "TEXT")
	private String pluginInfo;
	
	@Column(name = "original_filename")
	private String originalFilename;
	@Column(name = "file_location")
	private String fileLocation;
	
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Column(name = "last_modified_by")
	private String lastModifiedBy;
	@Column(name = "last_modified_at") //TODO: Implement modifying functionality 
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedAt;
	
	
	public Long getId() {
		return id;
	}
	public String getBundleSymbolicName() {
		return bundleSymbolicName;
	}
	public String getBundleVersion() {
		return bundleVersion;
	}
	public void setBundleVersion(String bundleVersion) {
		this.bundleVersion = bundleVersion;
	}
	public String getBundleName() {
		return bundleName;
	}
	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}
	public String getBundleVendor() {
		return bundleVendor;
	}
	public void setBundleVendor(String bundleVendor) {
		this.bundleVendor = bundleVendor;
	}
	public String getBundleDescription() {
		return bundleDescription;
	}
	public void setBundleDescription(String bundleDescription) {
		this.bundleDescription = bundleDescription;
	}
	public String getBundleActivator() {
		return bundleActivator;
	}
	public void setBundleActivator(String bundleActivator) {
		this.bundleActivator = bundleActivator;
	}
	public String getBundleImportPackage() {
		return bundleImportPackage;
	}
	public void setBundleImportPackage(String bundleImportPackage) {
		this.bundleImportPackage = bundleImportPackage;
	}
	public String getPluginName() {
		return pluginName;
	}
	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}
	public String getPluginDescription() {
		return pluginDescription;
	}
	public void setPluginDescription(String pluginDescription) {
		this.pluginDescription = pluginDescription;
	}
	public String getPluginInfo() {
		return pluginInfo;
	}
	public void setPluginInfo(String pluginInfo) {
		this.pluginInfo = pluginInfo;
	}
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
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
	public void setBundleSymbolicName(String bundleSymbolicName) {
		this.bundleSymbolicName = bundleSymbolicName;
	}
	
	@Override
	public String toString() {
		return "Plugin [id=" + id + ", bundleSymbolicName=" + bundleSymbolicName + ", bundleVersion=" + bundleVersion
				+ ", bundleName=" + bundleName + ", bundleVendor=" + bundleVendor + ", bundleDescription="
				+ bundleDescription + ", bundleActivator=" + bundleActivator + ", bundleImportPackage="
				+ bundleImportPackage + ", pluginName=" + pluginName + ", pluginDescription=" + pluginDescription
				+ ", plugininfo=" + pluginInfo + ", originalFilename=" + originalFilename + ", fileLocation="
				+ fileLocation + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", lastModifiedBy="
				+ lastModifiedBy + ", lastModifiedAt=" + lastModifiedAt + "]";
	}
	
}
