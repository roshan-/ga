package citi.ga.controller;

import java.util.Arrays;

import citi.ga.exportdata.ExportTask;
import citi.ga.exportdata.SinkType;
import citi.ga.model.GaDimensions;
import citi.ga.model.GaMetrics;
import citi.ga.service.AnalyticsService;

import com.google.api.services.analytics.Analytics;

public class AnalyticsController {
	Analytics analytics;

	public AnalyticsController() {
		analytics= AnalyticsService.getAnalytics();
	}

	public static void main(String[] args) {
		AnalyticsController control= new AnalyticsController();
		control.setup();
		control.exportProcess();
		control.importProcess();
	}
	
	public void setup() {
		
	}
	
	public void exportProcess() {
		try {
			ExportTask task= new ExportTask("96667371", "2015-01-01", Arrays.asList(GaMetrics.totalEvents), 
					Arrays.asList( 	GaDimensions.hour, GaDimensions.minute, GaDimensions.citiRequestId, 
									GaDimensions.eventCategory, GaDimensions.eventAction, GaDimensions.eventLabel), 
					Arrays.asList(GaDimensions.hour, GaDimensions.minute), 
					"eventSet", SinkType.FileSystemType, (long)60*60*1000);
			task.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void importProcess() {
	}
	
}
