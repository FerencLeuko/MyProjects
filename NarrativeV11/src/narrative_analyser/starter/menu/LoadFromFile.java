package narrative_analyser.starter.menu;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import narrative_analyser.process.Process;
import narrative_analyser.starter.ExitException;
import narrative_analyser.starter.Menu;

import static java.util.stream.Collectors.toCollection;

class LoadFromFile extends AbstractMenu {
	
	private static final Logger LOG = LogManager.getLogger(LoadFromFile.class);

	{
		name = "Load a previous analysis from file";
	}

	private final int filesPerPage = 20;

	public void printMenu() {
		Set<String> menuNames = new LinkedHashSet<>();
		for (Menu menu : getLoadOptions()) {
			menuNames.add(menu.getName());
		}
		printMenuNames(String.format("%n%s", "Load from file: "), menuNames);
	}

	@Override
	public void menuAction() throws ExitException {
		printMenu();
		int choice = select(getLoadOptions().length);
		int count = 0;
		for (Menu menu : getLoadOptions()) {
			if (count++ == choice) {
				LOG.info("File load: " + menu.getName());
				menu.menuAction();
			}
		}
	}

	private Menu[] getLoadOptions() {
		return new Menu[] { new Load(), new Restore() };
	}

	private class Load extends LoadFromFile {

		{
			name = "Load a saved explorer file";
		}

		@Override
		public void menuAction() throws ExitException {
			Set<String> fileNames = storage.getExplorerNames();
			if (fileNames.isEmpty()) {
				System.out.println("No saved explorer files available.");
			}
			while (!fileNames.isEmpty()) {
				Set<String> nextPage = createNextPage(fileNames);
				printMenuNames("Files available: ", nextPage);
				int choice = select(nextPage.size());
				if (choice == filesPerPage) {
					continue;
				}
				loadSelectedFile(nextPage, choice);
			}
		}

		private Set<String> createNextPage(Set<String> fileNames) {
			Set<String> nextPage = fileNames.stream().limit(filesPerPage).collect(toCollection(LinkedHashSet::new));
			fileNames.removeAll(nextPage);
			if (!fileNames.isEmpty()) {
				nextPage.add("Next page");
			}
			return nextPage;
		}

		private void loadSelectedFile(Set<String> nextPage, int choice) throws ExitException {
			int count = 0;
			for (String fileName : nextPage) {
				if (count++ == choice) {
					new Process(storage.loadExpl(fileName.substring(0, fileName.indexOf(".")))).run();
				}
			}
		}

	}

	private class Restore extends LoadFromFile {

		{
			name = "Restore an explorer file from autosave";
		}

		@Override
		public void menuAction() throws ExitException {
			new Process(storage.loadExpl("autoSave")).run();
		}

	}

}
