package ee.ut.cs.rum.database.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SystemParameter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "parameter_name")
	private String parameterName;
	@Column(name = "parameter_description")
	private String parameterDescription;
	@Column(name = "parameter_value")
	private String parameterValue;
	
	public Long getId() {
		return id;
	}
	
	public String getParameterName() {
		return parameterName;
	}
	
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
	public String getParameterDescription() {
		return parameterDescription;
	}
	
	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}
	
	public String getParameterValue() {
		return parameterValue;
	}
	
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	@Override
	public String toString() {
		return "SystemParameter [id=" + id + ", parameterName=" + parameterName + ", parameterDescription="
				+ parameterDescription + ", parameterValue=" + parameterValue + "]";
	}
}
