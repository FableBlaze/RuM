package ee.ut.cs.rum.interfaces;

import ee.ut.cs.rum.enums.ControllerUpdateType;

public interface RumUpdatableView {
	public void controllerUpdateNotify(ControllerUpdateType updateType, Object updatedEntity);
}
