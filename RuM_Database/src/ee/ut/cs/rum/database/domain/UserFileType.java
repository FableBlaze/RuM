package ee.ut.cs.rum.database.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_file_type")
public class UserFileType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	//TODO: Consider using a join table (many-to-many relation)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_file_id")
	private UserFile userFile;
	
	@Column(name = "type_name")
	private String typeName;

	public UserFile getUserFile() {
		return userFile;
	}

	public void setUserFile(UserFile userFile) {
		this.userFile = userFile;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		//userFile.getId() because otherwise it would result in an infinite loop 
		return "UserFileType [id=" + id + ", userFile=" + userFile.getId() + ", typeName=" + typeName + "]";
	}

	
}
