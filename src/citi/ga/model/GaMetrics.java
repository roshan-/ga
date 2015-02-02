package citi.ga.model;

import java.util.List;

public enum GaMetrics {
	users,
	newUsers,
	sessionsPerUser,
	sessions,
	visits,
	bounces,
	sessionDuration,
	timeOnSite,
	hits,
	pageLoadTime,
	domContentLoadedTime,
	totalEvents,
	uniqueEvents,
	eventValue;
	
	public static String fromList(List<GaMetrics> metricsList) {
		StringBuilder sb= new StringBuilder();
		for (GaMetrics metric : metricsList) {
			sb.append(",ga:").append(metric);
		}
		return sb.length()>1?sb.substring(1):"";
	}
}
