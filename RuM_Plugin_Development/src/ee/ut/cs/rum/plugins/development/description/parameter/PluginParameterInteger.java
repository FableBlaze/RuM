package ee.ut.cs.rum.plugins.development.description.parameter;

public class PluginParameterInteger extends PluginParameter {
	private int minValue;
	private int maxValue;
	private int defaultValue;
	
	public PluginParameterInteger() {
		super();
		super.setParameterType(PluginParameterType.INTEGER);
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(int defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return super.toString() + "PluginParameterInteger [minValue=" + minValue + ", maxValue="	+ maxValue 
				+ ", defaultValue=" + defaultValue + "]";
	}
	
}
