package citi.ga.exportdata;

import java.io.Closeable;
import java.io.IOException;

public interface ExportSink extends Closeable{
	public void writeEvent(String event) throws IOException;
	
	public void newLine() throws IOException;
}
