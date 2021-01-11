package narrative_analyser.process;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import narrative_analyser.analyser.StorageImpl;
import narrative_analyser.starter.ExitException;
import narrative_analyser.starter.Input;
import narrative_analyser.starter.Storage;
import narrative_analyser.starter.TextExplorer;

public class Process {

	private static final Logger LOG = LogManager.getLogger(Process.class);
	Input input = new Input();
	Storage storage = new StorageImpl();
	ActionMenu menu = new ActionMenu();
	Set<Action> actions;
	Set<String> actionNames;
	TextExplorer explorer;

	public Process(TextExplorer explorer) {
		this.explorer = explorer;
	}

	public void run() throws ExitException {
		LOG.info("Process started");
		System.out.println(explorer);
		runMenu();
	}

	private void runMenu() throws ExitException {
		initActions();
		initActionNames();
		while (true) {
			storage.autoSave(explorer);
			LOG.info("Autosave");
			menu.printMenuNames(String.format(("%n%s%n"), "Please choose from options: "), actionNames);
			int choice = menu.select(actionNames.size());
			int count = 0;
			for (Action action : actions) {
				if (count++ == choice) {
					LOG.info("Action: " + action.getName());
					action.runAction();
				}
			}
		}
	}

	private void initActions() {
		if (actions == null) {
			actions = new LinkedHashSet<>(new Action(explorer).getActionList());
		}
	}

	private void initActionNames() {
		if (actionNames == null) {
			actionNames = new LinkedHashSet<>();
			for (Action action : actions) {
				actionNames.add(action.getName());
			}
		}
	}

}
