package citi.ga.model;

import java.util.List;

public enum GaDimensions {
	browser,
	operatingSystem,
	mobileDeviceBranding,
	mobileDeviceModel,
	cityId,
	hour,
	minute,
	screenResolution,
	eventCategory,
	eventAction,
	eventLabel,
	citiRequestId () {
		public String toString() {
			return "dimension10";
		}
	};

	public static String fromList(List<GaDimensions> dimensionsList) {
		StringBuilder sb= new StringBuilder();
		for (GaDimensions metric : dimensionsList) {
			sb.append(",ga:").append(metric);
		}
		return sb.length()>1?sb.substring(1):"";
	}
}
