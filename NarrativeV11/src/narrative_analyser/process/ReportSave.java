package narrative_analyser.process;

import narrative_analyser.starter.TextExplorer;

class ReportSave extends Action{
	
	public ReportSave(TextExplorer explorer) {
		super(explorer);
	}

	{
		name = "Save the report to a report file";
	}
	
	@Override
	void runAction() {
		System.out.println("Please enter your filename to save the report to file: ");
		String fileName = input.askInput();
		storage.saveReport(explorer, fileName);
	}


}
