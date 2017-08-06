package ee.ut.cs.rum.plugins.development.description.parameter;

public class PluginParameterDouble extends PluginParameter {
	private Double minValue;
	private Double maxValue;
	private Double defaultValue;
	private int decimalPlaces;
	
	public PluginParameterDouble() {
		super();
		super.setParameterType(PluginParameterType.DOUBLE);
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public Double getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Double defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getDecimalPlaces() {
		return decimalPlaces;
	}

	public void setDecimalPlaces(Integer decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	@Override
	public String toString() {
		return "PluginParameterDouble [minValue=" + minValue + ", maxValue=" + maxValue + ", defaultValue="
				+ defaultValue + ", decimalPlaces=" + decimalPlaces + "]";
	}
}
