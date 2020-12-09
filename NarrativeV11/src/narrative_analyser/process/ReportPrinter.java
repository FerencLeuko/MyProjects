package narrative_analyser.process;

import narrative_analyser.starter.TextExplorer;

class ReportPrinter extends Action{
	
	public ReportPrinter(TextExplorer explorer) {
		super(explorer);
	}

	{
		name = "Print the report";
	}
	
	@Override
	void runAction() {
		System.out.println(explorer.getReport());
	}
}
