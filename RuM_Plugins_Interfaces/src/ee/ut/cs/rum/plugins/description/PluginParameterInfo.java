package ee.ut.cs.rum.plugins.description;

public class PluginParameterInfo {
	private String internalName;
	
	public PluginParameterInfo() {
	}
	
	public PluginParameterInfo(String internalName) {
		this.internalName=internalName;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	@Override
	public String toString() {
		return "PluginParameterInfo [internalName=" + internalName + "]";
	}

}
