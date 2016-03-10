package ee.ut.cs.rum.database.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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
	@Column(name = "activator")
	private String activator;
	@Column(name = "import_package")
	private String importPackage;
	@Column(name = "original_filename")
	private String originalFilename;

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

	@Override
	public String toString() {
		return "Plugin [id=" + id + ", symbolicName=" + symbolicName + ", version=" + version + ", name=" + name
				+ ", activator=" + activator + ", importPackage=" + importPackage + ", originalFilename="
				+ originalFilename + "]";
	}
	
	
}
