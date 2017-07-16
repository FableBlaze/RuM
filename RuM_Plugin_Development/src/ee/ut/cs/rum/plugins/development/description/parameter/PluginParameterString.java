package ee.ut.cs.rum.plugins.development.description.parameter;

public class PluginParameterString extends PluginParameter {
	private int maxInputLength;
	private String defaultValue;
	private String allowedCharacters;
	
	public PluginParameterString() {
		super();
		super.setParameterType(PluginParameterType.STRING);
	}

	public int getMaxInputLength() {
		return maxInputLength;
	}
	
	public void setMaxInputLength(int maxInputLength) {
		this.maxInputLength = maxInputLength;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public String getAllowedCharacters() {
		return allowedCharacters;
	}
	
	public void setAllowedCharacters(String allowedCharacters) {
		this.allowedCharacters = allowedCharacters;
	}
	
	@Override
	public String toString() {
		return super.toString()
				+ "PluginParameterInfoString [maxInputLength=" + maxInputLength + ", defaultValue=" + defaultValue + ", allowedCharacters=" + allowedCharacters + "]";
	}
}
