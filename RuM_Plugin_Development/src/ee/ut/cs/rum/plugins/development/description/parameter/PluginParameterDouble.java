package ee.ut.cs.rum.plugins.development.description.parameter;

public class PluginParameterDouble extends PluginParameter {
	private Double defaultValue;
	private Double minValue;
	private Double maxValue;
	private int decimalPlaces;
	
	public PluginParameterDouble() {
		super();
		super.setParameterType(PluginParameterType.DOUBLE);
	}

	public Double getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Double defaultValue) {
		this.defaultValue = defaultValue;
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
