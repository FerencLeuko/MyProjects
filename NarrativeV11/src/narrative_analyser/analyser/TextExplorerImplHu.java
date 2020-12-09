package narrative_analyser.analyser;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import narrative_analyser.starter.Language;

/**
 * Hungarian laguage version specifics: 1. counting all words in the text: suffix insensitive, a word and its suffixed
 * versions are counted into one group e.g. "almát" is counted to "alma" 2. counting positive and negative words: same
 * as above 3. counting next frequent words to a selected key word within a specified range: only the exact suffixed
 * version of the key word is selected in the text, and all other words are counted in the specified range, suffix
 * sensitive, and only the longer suffixed versions of the key word are not counted, e.g. key word: "alma", next word
 * "almát" is not counted. In order to skip suffix sensitiveness, user can choose non-language specific count, setting
 * language to NONE.
 */

class TextExplorerImplHu extends TextExplorerImpl {

	private String suffixHuRegex;
	
	TextExplorerImplHu(int serialNumber, String time, String textOriginal, Dictionary positiveWords, Dictionary negativeWords, Language language,
			String report) {
		super(serialNumber, time, textOriginal, positiveWords, negativeWords, language, report);
	}

	@Override
	protected Map<String, Integer> countSelectedWords(String text, Set<String> wordsToBeCounted) {
		Map<String, Integer> freqs = new TreeMap<>();
		for (String word : wordsToBeCounted) {
			for (String setContent : freqs.keySet()) {
				if (wordEqualsHU(word, setContent)) {
					word = word.length() > setContent.length() ? setContent : word;
				}
			}
			if (!freqs.containsKey(word)) {
				freqs.put(word, countWord(text, word));
			}
		}
		return freqs;
	}

	@Override
	protected boolean wordEquals(String word1, String word2) {
		return wordEqualsHU(word1, word2);
	}

	private boolean wordEqualsHU(String word1, String word2) {
		if (word1.equals(word2)) {
			return true;
		}
		String shorter = word1.length() > word2.length() ? word2.toLowerCase() : word1.toLowerCase();
		String longer = word1.length() > word2.length() ? word1.toLowerCase() : word2.toLowerCase();
		String wordHuRegex = "(" + shorter.substring(0, shorter.length() - 1) + ")" + "((?iu)[A-ZÁÉÍÓÖŐÚÜŰ]{1,2}?)(" + getSuffixHuRegex() + ")";
		if (longer.matches(wordHuRegex)) {
			Pattern p = Pattern.compile(shorter.substring(0, shorter.length() - 1));
			Matcher m = p.matcher(longer);
			if (m.find() && shorter.length() > 1) {
				return true;
			}
		}
		return false;
	}

	private String getSuffixHuRegex() {
		if (suffixHuRegex == null) {
			String suffixHu;
			try {
				suffixHu = storage.loadDictionaryFromString("suffixHu", Language.HU).getDictionaryString();
			} catch (Exception e) {
				suffixHu = properties.getSuffixHuDictionary();
			}
			StringBuilder b = new StringBuilder();
			b.append("((");
			for (String suffix : suffixHu.split(",")) {
				for (String letter : suffix.split("")) {
					b.append("[").append(letter).append("]");
				}
				b.append(")|(");
			}
			b.append("[t]))+");
			suffixHuRegex = b.toString();
		}
		return suffixHuRegex;
	}


}
