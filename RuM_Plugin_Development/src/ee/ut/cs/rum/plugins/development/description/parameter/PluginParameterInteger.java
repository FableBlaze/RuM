package ee.ut.cs.rum.plugins.development.description.parameter;

public class PluginParameterInteger extends PluginParameter {
	private Integer defaultValue;
	private Integer minValue;
	private Integer maxValue;
	
	public PluginParameterInteger() {
		super();
		super.setParameterType(PluginParameterType.INTEGER);
	}

	public Integer getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Integer defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public String toString() {
		return "PluginParameterInteger [minValue=" + minValue + ", maxValue=" + maxValue + ", defaultValue="
				+ defaultValue + "]";
	}
}
