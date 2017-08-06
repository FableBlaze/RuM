package ee.ut.cs.rum.plugins.development.description.parameter;

public class PluginParameterString extends PluginParameter {
	private Integer maxInputLength;
	private String defaultValue;
	private String allowedCharacters;
	
	public PluginParameterString() {
		super();
		super.setParameterType(PluginParameterType.STRING);
	}

	public Integer getMaxInputLength() {
		return maxInputLength;
	}
	
	public void setMaxInputLength(Integer maxInputLength) {
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
		return "PluginParameterString [maxInputLength=" + maxInputLength + ", defaultValue=" + defaultValue
				+ ", allowedCharacters=" + allowedCharacters + "]";
	}
}
