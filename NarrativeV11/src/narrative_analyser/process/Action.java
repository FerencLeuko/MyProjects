package narrative_analyser.process;

import java.util.ArrayList;
import java.util.List;

import narrative_analyser.starter.TextExplorer;

class Action extends Process {
	
	public Action(TextExplorer explorer) {
		super(explorer);
	}

	String name;
	
	String getName() {
		return name;
	}
	
	void runAction() {
	}
	
	private List<Action> actionList = new ArrayList<>();
	
	List<Action> getActionList(){
		if(actionList.isEmpty()){
			initActionList();
		}
		return actionList;
	}
	
	private void initActionList() {
		actionList.add(new TextAnalysis(explorer));
		actionList.add(new ExplorerSave(explorer));
		actionList.add(new ReportSave(explorer));
		actionList.add(new Comment(explorer));
		actionList.add(new TextPrinter(explorer));
		actionList.add(new ReportPrinter(explorer));
	}
}
