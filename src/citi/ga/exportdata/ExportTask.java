package citi.ga.exportdata;

import java.io.FileNotFoundException;
import java.util.List;

import citi.ga.model.GaDimensions;
import citi.ga.model.GaMetrics;
import citi.ga.service.AnalyticsService;

import com.google.api.services.analytics.model.GaData;
import com.google.api.services.analytics.model.GaData.ColumnHeaders;

public class ExportTask implements Runnable{
	private final String profileId;
	private final String date;
	private final List<GaDimensions> dimensions;
	private final List<GaMetrics> metrics;
	private final List<GaDimensions> sort;
	private final String exportSet;
	private final ExportSink sink;
	private final long interval;

	public ExportTask(String profileId, String date, List<GaMetrics> metrics, List<GaDimensions> dimensions, List<GaDimensions> sort, String exportSet, SinkType sink, long interval) 
			throws FileNotFoundException {
		this.profileId= profileId;
		this.date= date;
		this.dimensions= dimensions;
		this.metrics= metrics;
		this.sort= sort;
		this.exportSet= exportSet;
		this.sink= (sink == SinkType.FileSystemType)?new FileSystemSink(date+"-"+this.exportSet):new FileSystemSink(date+"-"+this.exportSet);
		this.interval= interval;
	}

	@Override
	public void run() {
		try {
			GaData data= AnalyticsService.getAnalytics().data().ga().get("ga:"+ profileId,
			        date, // Start date.
			        date, // End date.
			        GaMetrics.fromList(metrics)) // Metrics.
			        .setDimensions(GaDimensions.fromList(dimensions))
			        .setSort(GaDimensions.fromList(sort))
			        .setMaxResults(50000)
			        .execute();

		    if (data.getRows() == null || data.getRows().isEmpty()) {
		        System.out.println("No results Found.");
		      } else {

		        // Print column headers.
		        for (ColumnHeaders header : data.getColumnHeaders()) {
		          sink.writeEvent(String.format("%30s", header.getName()));
		        }
		        sink.newLine();

		        // Print actual data.
		        for (List<String> row : data.getRows()) {
		          for (String column : row) {
		        	  sink.writeEvent(String.format("%30s", column));
		          }
		          sink.newLine();;
		        }
		      }
		    sink.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
