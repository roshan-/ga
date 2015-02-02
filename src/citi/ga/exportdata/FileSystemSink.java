package citi.ga.exportdata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileSystemSink implements ExportSink {

	private final BufferedWriter f;
	public FileSystemSink(String f) throws FileNotFoundException {
		this.f= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(f))));
	}

	@Override
	public void close() throws IOException {
		f.close();
	}

	@Override
	public void writeEvent(String event) throws IOException {
		f.write(event);
	}

	public void newLine() throws IOException {
		f.newLine();
	}
}
