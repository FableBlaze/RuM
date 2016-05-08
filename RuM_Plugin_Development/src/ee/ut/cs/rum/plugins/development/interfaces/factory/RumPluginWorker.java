package ee.ut.cs.rum.plugins.development.interfaces.factory;

import java.io.File;

public interface RumPluginWorker {
	public int runWork(String configuration, File outputParent);
}
