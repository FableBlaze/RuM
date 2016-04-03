package ee.ut.cs.rum.plugins.development.description.parameter;

public class PluginParameterDouble extends PluginParameter {
	private double minValue;
	private double maxValue;
	private double defaultValue;
	private int decimalPlaces;
	
	public PluginParameterDouble() {
		super();
		super.setParameterType(PluginParameterType.DOUBLE);
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public double getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(double defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	public void setDecimalPlaces(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	@Override
	public String toString() {
		return super.toString() + "PluginParameterDouble [minValue=" + minValue + ", maxValue=" + maxValue + ", defaultValue="
				+ defaultValue + ", decimalPlaces=" + decimalPlaces + "]";
	}
	
	
}
