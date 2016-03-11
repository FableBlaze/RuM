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
@Table(name="plugin")
public class Plugin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "symbolic_name")
	private String symbolicName;
	@Column(name = "version")
	private String version;
	@Column(name = "name")
	private String name;
	@Column(name = "vendor")
	private String vendor;
	@Column(name = "description")
	private String description;
	@Column(name = "activator")
	private String activator;
	@Column(name = "import_package")
	private String importPackage;
	@Column(name = "original_filename")
	private String originalFilename;
	@Column(name = "uploaded_by")
	private String uploadedBy;
	@Column(name = "uploaded_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadedAt;
	@Column(name = "file_location")
	private String fileLocation;

	public Long getId() {
		return id;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getActivator() {
		return activator;
	}

	public void setActivator(String activator) {
		this.activator = activator;
	}

	public String getImportPackage() {
		return importPackage;
	}

	public void setImportPackage(String importPackage) {
		this.importPackage = importPackage;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Date getUploadedAt() {
		return uploadedAt;
	}

	public void setUploadedAt(Date uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	@Override
	public String toString() {
		return "Plugin [id=" + id + ", symbolicName=" + symbolicName + ", version=" + version + ", name=" + name
				+ ", vendor=" + vendor + ", description=" + description + ", activator=" + activator
				+ ", importPackage=" + importPackage + ", originalFilename=" + originalFilename + ", uploadedBy="
				+ uploadedBy + ", uploadedAt=" + uploadedAt + ", fileLocation=" + fileLocation + "]";
	}
	
}
