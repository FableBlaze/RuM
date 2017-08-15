package ee.ut.cs.rum.database.util.exceptions;

import ee.ut.cs.rum.database.domain.enums.SystemParameterName;

public class SystemParameterNotSetException extends Exception {
	private static final long serialVersionUID = -8457430877355127131L;
	
	public SystemParameterNotSetException(SystemParameterName systemParameterName) {
		super("System parameter " + systemParameterName.toString() + " not set");
	}
}
