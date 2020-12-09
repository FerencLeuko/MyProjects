package narrative_analyser.starter;

import java.util.Map;

import narrative_analyser.analyser.Dictionary;
import narrative_analyser.analyser.ExplorerBuilder;

public interface TextExplorer {

	public static ExplorerBuilder getBuilder(Language textLanguage) {
		return ExplorerBuilder.getBuilder(textLanguage);
	}

	String getTextOriginal();

	String getTextInLines(int width);

	int getSerialNumber();

	String getTime();

	Language getLanguage();

	String getLabel();

	int getNumChars();

	int getNumWords();

	Dictionary getPositiveWords();

	Dictionary getNegativeWords();

	Map<String, Integer> getFrequentWords();

	Map<String, Integer> getFrequentWordsByFreqs();

	Map<String, Integer> getFrequentPositiveWords();

	Map<String, Integer> getFrequentPositiveWordsByFreqs();

	Map<String, Integer> getFrequentNegativeWords();

	Map<String, Integer> getFrequentNegativeWordsByFreqs();

	Map<String, Map<String, Integer>> getNextFrequentWords(int range, String... keyWords);

	Map<String, Map<String, Integer>> getNextFrequentWordsByFreqs(int range, String... keyWords);

	String getReport();

	void addStringToReport(String string);

	String getNextFrequentWordsAsString(Map<String, Map<String, Integer>> nextFrequentWordsCount, int range);

}
