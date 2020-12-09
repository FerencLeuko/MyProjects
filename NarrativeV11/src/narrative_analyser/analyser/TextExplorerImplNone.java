package narrative_analyser.analyser;

import narrative_analyser.starter.Language;

class TextExplorerImplNone extends TextExplorerImpl {

	TextExplorerImplNone(int serialNumber, String time, String textOriginal, Dictionary positiveWords, Dictionary negativeWords, Language language,
			String report) {
		super(serialNumber, time, textOriginal, positiveWords, negativeWords, language, report);
	}
	
	@Override
	protected boolean wordEquals(String word1, String word2) {
		return word1.equals(word2);
	}

}
