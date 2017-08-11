package ee.ut.cs.rum.plugins.development.description.parameter;

import com.google.gson.JsonParseException;

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
		if (defaultValue==null) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - defaultValue can not be empty");
		}
		this.defaultValue = defaultValue;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		if (maxValue!=null && minValue>maxValue) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - minValue can not be smaller than maxValue");
		}
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		if (minValue!=null && maxValue<minValue) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - maxValue can not be smaller than minValue");
		}
		this.maxValue = maxValue;
	}

	@Override
	public String toString() {
		return "PluginParameterInteger [minValue=" + minValue + ", maxValue=" + maxValue + ", defaultValue="
				+ defaultValue + "]";
	}
}
