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
//	      Upload u= AnalyticsService.getAnalytics().management().uploads().uploadData("96667371", "UA-53906132-4", "kBHCpZSTTuC9cLsRsyiryw", source.getInputStreamContent()).execute();
	      Upload u= AnalyticsService.getAnalytics().management().uploads().uploadData("96667371", "UA-53906132-5", "aMl4xcLFQfOYi83VmBjOzw", source.getInputStreamContent()).execute();
	      System.out.println(u.getStatus());
	    } catch (IOException e) {
	      System.out.println(e.getMessage());
	    }
	}
}
