package narrative_analyser.analyser;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import narrative_analyser.starter.Language;
import narrative_analyser.starter.ProgramProperties;
import narrative_analyser.starter.Storage;
import narrative_analyser.starter.TextExplorer;

class TextExplorerImpl implements TextExplorer {

	protected static int number;

	public static int getNextNumber() {
		return ++number;
	}

	private static final Logger LOG = LogManager.getLogger(TextExplorerImpl.class);

	protected final Storage storage = new StorageImpl();
	protected final ProgramProperties properties = ProgramProperties.getProperties();
	private final Language language;
	private final String label;
	private final int serialNumber;
	private final String time;
	private final String nonAlphaBet;
	private Collator collator;
	private final String textOriginal;
	private final String text;
	private final int numChars;
	private final int numWords;
	private Dictionary positiveWordsDictionary;
	private Dictionary negativeWordsDictionary;
	private Set<String> positiveWordsSet = new TreeSet<>(collator);
	private Set<String> negativeWordsSet = new TreeSet<>(collator);
	private Map<String, Integer> frequentWordsMap = new TreeMap<>(collator);
	private Map<String, Integer> frequentPositiveWordsMap = new TreeMap<>(collator);
	private Map<String, Integer> frequentNegativeWordsMap = new TreeMap<>(collator);
	private String report;

	TextExplorerImpl(int serialNumber, String time, String textOriginal, Dictionary positiveWords, Dictionary negativeWords, Language language,
			String report) {
		number = this.serialNumber = serialNumber;
		storage.saveLastSerialNumber(this.serialNumber);
		this.time = time;
		this.language = language;
		if (language == null) {
			language = properties.getLanguage();
		}
		this.label = language.getLabel();
		this.nonAlphaBet = language.getNonAlphaBet();
		this.collator = language.getCollator();
		this.textOriginal = textOriginal;
		this.text = textOriginal.replaceAll(nonAlphaBet, "").toLowerCase();
		try {
			this.positiveWordsDictionary = positiveWords;
			positiveWordsSet = positiveWordsDictionary.getDictionarySet();
		} catch (NullPointerException e) {
		}
		try {
			this.negativeWordsDictionary = negativeWords;
			negativeWordsSet = negativeWordsDictionary.getDictionarySet();
		} catch (NullPointerException e) {
		}
		numChars = textOriginal.length();
		numWords = getTextOriginal().split(" ").length;
		this.report = report == null ? toString() : report;
		LOG.info("TextExplorer started");
	}

	@Override
	public String getTextOriginal() {
		return textOriginal;
	}

	@Override
	public String getTextInLines(int width) {
		return String.format("%n%n%s%n", String.join(String.format("%n"), breakToLines(textOriginal, width)));
	}

	@Override
	public int getSerialNumber() {
		return serialNumber;
	}

	@Override
	public String getTime() {
		return time;
	}

	@Override
	public Language getLanguage() {
		return language;
	}

	@Override
	public Dictionary getPositiveWords() {
		return positiveWordsDictionary;
	}

	@Override
	public Dictionary getNegativeWords() {
		return negativeWordsDictionary;
	}

	@Override
	public Map<String, Integer> getFrequentWords() {
		if (frequentWordsMap.isEmpty()) {
			frequentWordsMap = countAllWords(text);
		}
		return frequentWordsMap;
	}

	@Override
	public Map<String, Integer> getFrequentWordsByFreqs() {
		if (frequentWordsMap.isEmpty()) {
			getFrequentWords();
		}
		return sortByValues(frequentWordsMap);
	}

	@Override
	public Map<String, Integer> getFrequentPositiveWords() {
		if (!positiveWordsSet.isEmpty() && frequentPositiveWordsMap.isEmpty()) {
			frequentPositiveWordsMap = countSelectedWords(text, positiveWordsSet);
		}
		return frequentPositiveWordsMap;
	}

	@Override
	public Map<String, Integer> getFrequentPositiveWordsByFreqs() {
		if (frequentPositiveWordsMap.isEmpty()) {
			getFrequentPositiveWords();
		}
		return sortByValues(frequentPositiveWordsMap);
	}

	@Override
	public Map<String, Integer> getFrequentNegativeWords() {
		if (!negativeWordsSet.isEmpty() && frequentNegativeWordsMap.isEmpty()) {
			frequentNegativeWordsMap = countSelectedWords(text, negativeWordsSet);
		}
		return frequentNegativeWordsMap;
	}

	@Override
	public Map<String, Integer> getFrequentNegativeWordsByFreqs() {
		if (frequentNegativeWordsMap.isEmpty()) {
			getFrequentNegativeWords();
		}
		return sortByValues(frequentNegativeWordsMap);
	}

	@Override
	public Map<String, Map<String, Integer>> getNextFrequentWords(int range, String... keyWords) {
		Map<String, Map<String, Integer>> nextFrequentWords = new TreeMap<>();
		for (String keyWord : keyWords) {
			nextFrequentWords.put(keyWord, getNextFrequentWords(range, keyWord));
		}
		return nextFrequentWords;
	}

	@Override
	public Map<String, Map<String, Integer>> getNextFrequentWordsByFreqs(int range, String... keyWords) {
		Map<String, Map<String, Integer>> nextFrequentWords = new TreeMap<>();
		for (String keyWord : keyWords) {
			nextFrequentWords.put(keyWord, getNextFrequentWordsByFreqs(range, keyWord));
		}
		return nextFrequentWords;
	}

	private Map<String, Integer> getNextFrequentWords(int range, String keyWord) {
		Map<String, Integer> freqs = new TreeMap<>();
		List<String> words = Arrays.asList(text.split(" "));
		if (range > words.size()) {
			range = words.size();
		}
		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).equals(keyWord)) {
				String part = cutPartOfText(range, words, i);
				addWordCountsToMap(freqs, countAllWords(part));
				freqs.remove(words.get(i));
				i += range;
			}
		}
		return freqs;
	}

	private Map<String, Integer> getNextFrequentWordsByFreqs(int range, String keyWord) {
		return sortByValues(getNextFrequentWords(range, keyWord));
	}

	@Override
	public String getReport() {
		return report;
	}

	@Override
	public void addStringToReport(String string) {
		StringBuilder b = new StringBuilder();
		b.append(report).append(string);
		report = b.toString();
	}

	@Override
	public String getNextFrequentWordsAsString(Map<String, Map<String, Integer>> count, int range) {
		StringBuilder b = new StringBuilder();
		b.append(String.format("%n%n%s%d%s", "The results for frequent words in the range of ", range, ", the given key words: "));
		for (Map.Entry<String, Map<String, Integer>> wordEntry : count.entrySet()) {
			b.append(String.format("%n%n%s%s%s", "Key word: '", wordEntry.getKey(), "', frequent words in its range: "));
			int numWords = 0;
			for (Map.Entry<String, Integer> entry : wordEntry.getValue().entrySet()) {
				numWords += entry.getValue();
			}
			for (Map.Entry<String, Integer> entry : wordEntry.getValue().entrySet()) {
				b.append(String.format("%n%-12s     %5d      %5.1f %%", entry.getKey(), entry.getValue(), entry.getValue() * 100d / numWords));
			}
		}
		return b.toString();
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(String.format("%n%s%d%n", "Serial number: ", serialNumber));
		b.append(String.format("%s%s%n", "Date: ", time));
		b.append(String.format("%s%s%n", "Text language: ", getLabel()));
		b.append(String.format("Length of the text: %d chars, %d words.%n", numChars, numWords));
		b.append(String.format("%n%s%n", "Frequent words, frequencies, relative frequencies: "));
		for (Map.Entry<String, Integer> entry : getFrequentWordsByFreqs().entrySet()) {
			b.append(String.format("%n%-12s     %5d      %5.1f %%", entry.getKey(), entry.getValue(), entry.getValue() * 100d / numWords));
		}
		if (!positiveWordsSet.isEmpty()) {
			b.append(String.format("%n%n%s%n", "Positive words (set by user): "));
			b.append(positiveWordsSet.toString().substring(1, positiveWordsSet.toString().length() - 1));
			b.append(String.format("%n%n%s%n", "Positive words, frequencies, relative frequencies: "));
			for (Map.Entry<String, Integer> entry : getFrequentPositiveWordsByFreqs().entrySet()) {
				b.append(String.format("%n%-12s     %5d      %5.1f %%", entry.getKey(), entry.getValue(), entry.getValue() * 100d / numWords));
			}
		}
		if (!negativeWordsSet.isEmpty()) {
			b.append(String.format("%n%n%s%n", "Negative words (set by user) : "));
			b.append(negativeWordsSet.toString().substring(1, negativeWordsSet.toString().length() - 1));
			b.append(String.format("%n%n%s%n", "Negative words, frequencies, relative frequencies: "));
			for (Map.Entry<String, Integer> entry : getFrequentNegativeWordsByFreqs().entrySet()) {
				b.append(String.format("%n%-12s     %5d      %5.1f %%", entry.getKey(), entry.getValue(), entry.getValue() * 100d / numWords));
			}
		}
		return b.toString();
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public int getNumChars() {
		return numChars;
	}

	@Override
	public int getNumWords() {
		return numWords;
	}

	private List<String> breakToLines(String text, int width) {
		List<String> lines = new LinkedList<>();
		int start = 0, end = 0;
		while (start < text.length() - width) {
			end = text.lastIndexOf(" ", start + width);
			lines.add(text.substring(start, end));
			start = end + 1;
		}
		lines.add(text.substring(start));
		return lines;
	}

	private String cutPartOfText(int range, List<String> words, int center) {
		int start = center - range < 0 ? 0 : center - range;
		int end = center + range + 1 > words.size() ? words.size() : center + range + 1;
		return String.join(" ", words.subList(start, end));
	}

	private Map<String, Integer> addWordCountsToMap(Map<String, Integer> oldMap, Map<String, Integer> newMap) {
		for (Map.Entry<String, Integer> newEntry : newMap.entrySet()) {
			String key = newEntry.getKey();
			Integer oldValue = oldMap.get(key), newValue = newEntry.getValue();
			oldMap.put(key, (oldMap.containsKey(key) ? (oldValue + newValue) : newValue));
		}
		return oldMap;
	}

	protected int countWord(String string, String keyWord) {
		int freq = 0;
		for (String word : string.split(" ")) {
			if (wordEquals(word, keyWord.strip())) {
				freq++;
			}
		}
		return freq;
	}

	protected Map<String, Integer> countSelectedWords(String text, Set<String> wordsToBeCounted) {
		Map<String, Integer> freqs = new TreeMap<>();
		for (String word : wordsToBeCounted) {
			freqs.put(word, countWord(text, word));
		}
		return freqs;
	}

	private Map<String, Integer> countAllWords(String sourceText) {
		Set<String> wordSet = new TreeSet<>(Arrays.asList(sourceText.toLowerCase().split(" ")));
		return countSelectedWords(sourceText, wordSet);
	}

	private <K, V extends Comparable<V>> Map<K, V> sortByValues(Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {

			public int compare(K key1, K key2) {
				int compare = map.get(key1).compareTo(map.get(key2));
				if (compare == 0)
					return 1;
				else
					return -compare;
			}

		};
		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}

	protected boolean wordEquals(String word1, String word2) {
		return word1.equals(word2);
	}

}
