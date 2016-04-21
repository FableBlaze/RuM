package ee.ut.cs.rum.plugins.development.interfaces.factory;

import java.io.File;

public interface RumPluginWorker {
	public Object runWork(String configuration, File outputParent);
}
