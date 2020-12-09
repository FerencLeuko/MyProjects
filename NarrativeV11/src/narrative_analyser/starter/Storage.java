package narrative_analyser.starter;

import java.util.Set;

import narrative_analyser.analyser.Dictionary;
import narrative_analyser.analyser.StorageImpl;

public interface Storage {
	
	static Storage getInstance() {
		return new StorageImpl();
	}
	
	String loadText(String fileName);

	void saveExpl(TextExplorer analysis, String fileName);
	
	TextExplorer loadExpl(String fileName);
	
	void saveReport(TextExplorer analysis, String fileName);
		
	String loadReport(String fileName);
		
	void saveDictionary(Set<String> dictionaryWords, Language language, String fileName);
	
	void saveDictionary(Dictionary dictionary, String fileName);
	
	Dictionary loadDictionaryFromString(String fileName, Language language);
	
	Dictionary loadDictionary(String fileName);
	
	Set<String> getTextNames();
	
	Set<String> getExplorerNames();

	Set<String> getReportNames();
	
	Set<String> getDictionaryNames();
	
	Set<String> getFileNames(String directory, String extension);
	
	void autoSave(TextExplorer analysis);
	
	public int loadLastSerialNumber();
	
	public void saveLastSerialNumber(int last);
	
}
