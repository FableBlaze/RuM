package ee.ut.cs.rum.plugins.development.description.parameter;

import com.google.gson.JsonParseException;

public class PluginParameterInteger extends PluginParameter {
	private Integer defaultValue;
	private Integer minValue;
	private Integer maxValue;
	
	public PluginParameterInteger() {
		super(PluginParameterType.INTEGER);
	}

	public Integer getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Integer defaultValue) {
		if (defaultValue==null) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - defaultValue can not be empty");
		}
		if (minValue!=null && defaultValue<minValue) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - defaultValue can not be smaller than minValue");
		}
		if (maxValue!=null && defaultValue>maxValue) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - defaultValue can not be greater than maxValue");
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
		if (defaultValue!=null && minValue!=null && minValue>defaultValue) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - minValue can not be greater than defaultValue");
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
		if (defaultValue!=null && maxValue!=null && maxValue<defaultValue) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - maxValue can not be smaller than defaultValue");
		}
		this.maxValue = maxValue;
	}

	@Override
	public String toString() {
		return "PluginParameterInteger [minValue=" + minValue + ", maxValue=" + maxValue + ", defaultValue="
				+ defaultValue + "]";
	}
}
