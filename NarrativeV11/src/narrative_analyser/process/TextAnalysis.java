package narrative_analyser.process;

import narrative_analyser.starter.TextExplorer;

class TextAnalysis extends Action {

	public TextAnalysis(TextExplorer explorer) {
		super(explorer);
	}

	{
		name = "Analyse text";
	}

	@Override
	void runAction() {
		System.out.println("Search for frequent words next to selected key words.");
		System.out.println("Please enter the range (number of words) to search within: ");
		int range = 1;
		do {
			try {
				String answer = input.askInput();
				if (answer.equals(menu.exit)) {
					return;
				}
				range = Integer.parseInt(answer);
			} catch (NumberFormatException e) {
				System.out.println("Please enter the range of the number of words!");
			}
		} while (range < 0);
		System.out.println("Please enter the key words, separated with ',': ");
		String[] keyWords = input.askInput().replaceAll(" ", "").split(",");
		String freqs = explorer.getNextFrequentWordsAsString(explorer.getNextFrequentWordsByFreqs(range, keyWords), range);
		System.out.println(freqs);
		System.out.printf("%nWould like to add this to the report? Y / N : ");
		if (!input.askInput().toUpperCase().equals("N")) {
					explorer.addStringToReport(freqs);
		}
	}

}
