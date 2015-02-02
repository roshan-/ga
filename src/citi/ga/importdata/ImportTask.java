package citi.ga.importdata;

import java.io.FileNotFoundException;
import java.io.IOException;

import citi.ga.service.AnalyticsService;

import com.google.api.services.analytics.model.Upload;

public class ImportTask implements Runnable{
	private final ImportSource source;
	private final String importSet; 

	public ImportTask(String importSet, SourceType type) 
			throws FileNotFoundException {
		this.importSet= importSet;
		this.source= (type == SourceType.FileSystemType)?new FileSystemSource(this.importSet+".csv"):new FileSystemSource(this.importSet+".csv");
	}

	@Override
	public void run() {
	    try {
//	      Upload uQA= analytics.management().uploads().uploadData("96667371", "UA-53906132-4", "GkcrBboGSEas_qNZvBt58w", data).execute();
	      Upload u3936= AnalyticsService.getAnalytics().management().uploads().uploadData("96667371", "UA-53906132-5", "H2OefjqiRIavtH5mOl5XYQ", source.getInputStreamContent()).execute();
	      System.out.println(u3936.getErrors().toString());
	    } catch (IOException e) {
	      System.out.println(e.getMessage());
	    }
	}
}
