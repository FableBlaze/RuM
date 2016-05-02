package ee.ut.cs.rum.administration.internal.util;

import ee.ut.cs.rum.database.domain.SystemParameter;
import ee.ut.cs.rum.database.domain.enums.SystemParameterName;
import ee.ut.cs.rum.database.util.SystemParameterAccess;

public final class SystemParametersData {
	
	private SystemParametersData() {
	}
	
	public static void initializeSystemParameters() {
		SystemParameter systemParameter = new SystemParameter();
		systemParameter.setName(SystemParameterName.PLUGIN_PATH.toString());
		systemParameter.setDescription("Location of plugin jars");
		SystemParameterAccess.addSystemParameterDataToDb(systemParameter);
		
		systemParameter = new SystemParameter();
		systemParameter.setName(SystemParameterName.USER_FILE_PATH.toString());
		systemParameter.setDescription("Location of user uploaded files");
		SystemParameterAccess.addSystemParameterDataToDb(systemParameter);
		
		systemParameter = new SystemParameter();
		systemParameter.setName(SystemParameterName.TASK_RESULTS_ROOT.toString());
		systemParameter.setDescription("Location of task output folders");
		SystemParameterAccess.addSystemParameterDataToDb(systemParameter);
	}
}
