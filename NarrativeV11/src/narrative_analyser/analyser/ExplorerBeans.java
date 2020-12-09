package narrative_analyser.analyser;

import java.io.Serializable;

import narrative_analyser.starter.Language;

public class ExplorerBeans implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int serialNumber;
	private String time;
	private Language language;
	private String textOriginal;
	private String positiveWordsString;
	private String negativeWordsString;
	private String report;
	
	public ExplorerBeans(TextExplorerImpl ex) {
		this.serialNumber = ex.getSerialNumber();
		this.time = ex.getTime();
		this.language = ex.getLanguage();
		this.textOriginal = ex.getTextOriginal();
		try {
			this.positiveWordsString = ex.getPositiveWords().getDictionaryString();
		} catch (NullPointerException e) {
		}
		try {
			this.negativeWordsString = ex.getNegativeWords().getDictionaryString();
		} catch (NullPointerException e) {
		}
		this.report = ex.getReport();
	}	

	public ExplorerBeans() {
	}
	
	public String getTextOriginal() {
		return textOriginal;
	}
	
	public void setTextOriginal(String textOriginal) {
		this.textOriginal = textOriginal;
	}

	public String getPositiveWords() {
		return positiveWordsString;
	}

	public void setPositiveWords(String positiveWords) {
		this.positiveWordsString = positiveWords;
	}

	public String getNegativeWords() {
		return negativeWordsString;
	}
	
	public void setNegativeWords(String negativeWords) {
		this.negativeWordsString = negativeWords;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public String getReport() {
		return report;
	}
	
	public void setReport(String report) {
		this.report = report;
	}

	
}
