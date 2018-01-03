package ee.ut.cs.rum.plugins.development.description.parameter;

import com.google.gson.JsonParseException;

public class PluginParameterDouble extends PluginParameter {
	private Double defaultValue;
	private Double minValue;
	private Double maxValue;
	private Integer decimalPlaces;
	
	public PluginParameterDouble() {
		super(PluginParameterType.DOUBLE);
	}

	public Double getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Double defaultValue) {
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
	
	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		if (maxValue!=null && minValue>maxValue) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - minValue can not be smaller than maxValue");
		}
		if (defaultValue!=null && minValue!=null && minValue>defaultValue) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - minValue can not be greater than defaultValue");
		}
		if (defaultValue!=null && maxValue!=null && maxValue<defaultValue) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - maxValue can not be smaller than defaultValue");
		}
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		if (minValue!=null && maxValue<minValue) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - maxValue can not be smaller than minValue");
		}
		this.maxValue = maxValue;
	}

	public Integer getDecimalPlaces() {
		return decimalPlaces;
	}

	public void setDecimalPlaces(Integer decimalPlaces) {
		if (decimalPlaces==null || decimalPlaces<1) {
			throw new JsonParseException(this.getClass().getSimpleName() + " - decimalPlaces can not be smaller than 1");
		}
		this.decimalPlaces = decimalPlaces;
	}

	@Override
	public String toString() {
		return "PluginParameterDouble [minValue=" + minValue + ", maxValue=" + maxValue + ", defaultValue="
				+ defaultValue + ", decimalPlaces=" + decimalPlaces + "]";
	}
}
