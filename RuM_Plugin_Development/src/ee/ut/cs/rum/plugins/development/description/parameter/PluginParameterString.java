package ee.ut.cs.rum.plugins.development.description.parameter;

import com.google.gson.JsonParseException;

public class PluginParameterString extends PluginParameter {
	private String defaultValue;
	private Integer maxInputLength;
	private String allowedCharacters;
	
	public PluginParameterString() {
		super();
		super.setParameterType(PluginParameterType.STRING);
	}

	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getMaxInputLength() {
		return maxInputLength;
	}
	
	public void setMaxInputLength(Integer maxInputLength) {
		if (maxInputLength!=null && maxInputLength<0) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - maxInputLength can not be negative");
		}
		this.maxInputLength = maxInputLength;
	}
	
	public String getAllowedCharacters() {
		return allowedCharacters;
	}
	
	public void setAllowedCharacters(String allowedCharacters) {
		if (allowedCharacters!=null && allowedCharacters.equals("")) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - allowedCharacters can not be an empty string");
		}
		this.allowedCharacters = allowedCharacters;
	}

	@Override
	public String toString() {
		return "PluginParameterString [maxInputLength=" + maxInputLength + ", defaultValue=" + defaultValue
				+ ", allowedCharacters=" + allowedCharacters + "]";
	}
}
