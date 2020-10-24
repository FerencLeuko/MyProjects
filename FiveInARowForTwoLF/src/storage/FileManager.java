package storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FileManager {

	private List<String> fileNameList = new LinkedList<String>();
	private String fileNames;
	private String currentFileName;

	public String getFileNames() {
		if (fileNames == null || fileNameList.isEmpty()) {
			initFileNamesList();
		}
		return fileNames;
	}

	private String loadFileNames() {
		fileNames = readTextFromFile("fileNames");
		return fileNames;
	}

	public void saveGame(String fileName, String data) {
		if (fileNameList.isEmpty()) {
			initFileNamesList();
		}
		if ((!fileName.equals(currentFileName) || currentFileName == null) && fileNameList.contains(fileName)) {
			throw new IllegalArgumentException("Már létező filenév.");
		} else {
			writeTextToFile(fileName, data);
			updateFileNamesList(fileName);
			currentFileName = fileName;
		}
	}

	public String loadGame(String fileName) {
		String gameData = readTextFromFile(fileName);
		currentFileName = fileName;
		return gameData;
	}

	private void initFileNamesList() {
		try {
			for (String fileName : loadFileNames().split(", ")) {
				fileNameList.add(fileName);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void updateFileNamesList(String fileName) {
		if (!fileNameList.contains(fileName)) {
			fileNameList.add(fileName);
			fileNames = fileNameList.toString().replaceAll("[\\[]", "").replaceAll("[\\]]", "");
			writeTextToFile("fileNames", fileNames);
		}
	}

	private String readTextFromFile(String source) {
		try {
			return Files.readString(Paths.get(source), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Hiba történt fájl olvasáskor: " + e.getMessage());
			return null;
		}
	}

	private void writeTextToFile(String fileName, String text) {
		try {
			Files.writeString(Paths.get(fileName), text, StandardCharsets.UTF_8);
		} catch (Exception ex) {
			System.err.println("Hiba történt fájl írásakor: " + ex.getMessage());
		}
	}

}
