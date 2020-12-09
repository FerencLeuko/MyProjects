package narrative_analyser.process;

import narrative_analyser.starter.TextExplorer;

class Comment extends Action{
	
	public Comment(TextExplorer explorer) {
		super(explorer);
	}

	{
		name = "Add comments to the report";
	}
	
	@Override
	void runAction() {
		System.out.println("Please enter your comments to be added to the report: ");
		explorer.addStringToReport(String.format("%n%n%s%n", "Comments: "));
		explorer.addStringToReport(input.askInput());
	}


}
