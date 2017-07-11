package ee.ut.cs.rum.database.domain;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="sub_task_dependency")
public class SubTaskDependency {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@JoinColumn(name = "required_by_sub_task_fk")
	private SubTask requiredBySubTask;
	@JoinColumn(name = "fulfilled_by_sub_task_fk")
	private SubTask fulfilledBySubTask;
	
	@Column(name = "required_for_parameter")
	private String requiredForParameter;
	@Column(name = "file_name")
	private String fileName;
	
	public SubTask getRequiredBySubTask() {
		return requiredBySubTask;
	}
	public void setRequiredBySubTask(SubTask requiredBySubTask) {
		this.requiredBySubTask = requiredBySubTask;
		if (requiredBySubTask.getRequiredDependencies()==null) {
			requiredBySubTask.setRequiredDependencies(new ArrayList<SubTaskDependency>());
		}
		if (!requiredBySubTask.getRequiredDependencies().contains(requiredBySubTask)) {
			requiredBySubTask.getRequiredDependencies().add(this);
		}
	}
	public SubTask getFulfilledBySubTask() {
		return fulfilledBySubTask;
	}
	public void setFulfilledBySubTask(SubTask fulfilledBySubTask) {
		this.fulfilledBySubTask = fulfilledBySubTask;
		if (fulfilledBySubTask.getFulfilledDependencies()==null) {
			fulfilledBySubTask.setFulfilledDependencies(new ArrayList<SubTaskDependency>());
		}
		if (!fulfilledBySubTask.getFulfilledDependencies().contains(fulfilledBySubTask)) {
			fulfilledBySubTask.getFulfilledDependencies().add(this);
		}
	}
	public String getRequiredForParameter() {
		return requiredForParameter;
	}
	public void setRequiredForParameter(String requiredForParameter) {
		this.requiredForParameter = requiredForParameter;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "SubTaskDependency [id=" + id + ", requiredBySubTask=" + requiredBySubTask.getId() + ", fulfilledBySubTask="
				+ fulfilledBySubTask.getId() + ", requiredForParameter=" + requiredForParameter + ", fileName=" + fileName
				+ "]";
	}
}
