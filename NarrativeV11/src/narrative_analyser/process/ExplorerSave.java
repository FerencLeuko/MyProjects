package narrative_analyser.process;

import narrative_analyser.starter.TextExplorer;

class ExplorerSave extends Action {

	public ExplorerSave(TextExplorer explorer) {
		super(explorer);
	}

	{
		name = "Save as";
	}

	@Override
	void runAction() {
		System.out.println("Please enter your filename to save the explorer to file: ");
		String fileName = input.askInput();
		storage.saveExpl(explorer, fileName);
	}

}
