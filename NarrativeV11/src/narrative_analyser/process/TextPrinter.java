package narrative_analyser.process;

import narrative_analyser.starter.TextExplorer;

class TextPrinter extends Action {

	public TextPrinter(TextExplorer explorer) {
		super(explorer);
	}

	{
		name = "Print the original text";
	}

	private final int pageWidth = 80;

	@Override
	void runAction() {
		System.out.println(explorer.getTextInLines(pageWidth));
	}

}
