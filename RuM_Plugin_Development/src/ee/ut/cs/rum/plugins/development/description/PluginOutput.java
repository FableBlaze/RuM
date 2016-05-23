package ee.ut.cs.rum.plugins.development.description;

import java.util.Arrays;

public class PluginOutput {
	private String fileName;
	private String[] fileTypes;
	
	public PluginOutput() {
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String[] getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(String[] fileTypes) {
		for (int i = 0; i < fileTypes.length; i++) {
			fileTypes[i]=fileTypes[i].toLowerCase();
		}
		this.fileTypes = fileTypes;
	}
	
	@Override
	public String toString() {
		return "PluginOutput [fileName=" + fileName + ", fileTypes=" + Arrays.toString(fileTypes) + "]";
	}
}
