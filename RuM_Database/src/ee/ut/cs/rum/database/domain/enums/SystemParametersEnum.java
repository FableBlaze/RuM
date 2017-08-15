package ee.ut.cs.rum.database.domain.enums;

public enum SystemParametersEnum {
	PLUGIN_PATH("Location of plugin jars"),
	USER_FILE_PATH("Location of user uploaded files"),
	TASK_RESULTS_ROOT("Location of task output folders");
	
	private String description;
	
	private SystemParametersEnum(String description) {
		this.description=description;
	}
	
	public String getDescription() {
        return description;
    }
}
