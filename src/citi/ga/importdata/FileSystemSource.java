package citi.ga.importdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.google.api.client.http.InputStreamContent;

public class FileSystemSource implements ImportSource {

	private final InputStreamContent inputStream;
	public FileSystemSource(String f) throws FileNotFoundException {
		File file= new File(f);
		this.inputStream= new InputStreamContent("application/octet-stream", new FileInputStream(file));
		inputStream.setLength(file.length());
	}
	
	@Override
	public InputStreamContent getInputStreamContent() {
		return inputStream;
	}
}
