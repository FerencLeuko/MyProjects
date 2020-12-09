package narrative_analyser.starter.menu;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import narrative_analyser.analyser.Dictionary;
import narrative_analyser.process.Process;
import narrative_analyser.starter.ExitException;
import narrative_analyser.starter.Language;
import narrative_analyser.starter.TextExplorer;

class StartNewAnalysis extends AbstractMenu {
	
	private static final Logger LOG = LogManager.getLogger(StartNewAnalysis.class);

	{
		name = "Start a new text analysis";
	}

	public void printMenu() {
	}

	@Override
	public void menuAction() throws ExitException {
		System.out.println(name);
		
		TextExplorer exp = TextExplorer.getBuilder(selectLanguage()).//
				setText(askForText("Please choose a text for analysis.")).//
				setPositiveWords(askForDictionary("Please set the positive words dictionary.")).//
				setNegativeWords(askForDictionary("Please set the negative words dictionary.")).//
				build();
		LOG.info("New TextExplorer built.");
		new Process(exp).run();
	}

	private Language selectLanguage() throws ExitException {
		Language[] languages = Language.values();
		String[] names = new String[languages.length];
		int count = 0;
		for (Language language : languages) {
			names[count++] = language.getLabel();
		}
		printMenuNames("Please select text language: ", names);
		return languages[select(languages.length)];
	}

	private String askForText(String prompt) throws ExitException {
		System.out.println(prompt);
		String text = null;
		do {
			printMenuNames("", "Load a text", "Enter a text");
			switch (select(2)) {
			case 0 -> text = loadTextFromFile();
			case 1 -> text = inputText("Please enter your text: ");
			}
		} while (text == null);
		return text;
	}

	private String askForDictionary(String prompt) throws ExitException {
		System.out.println(prompt);
		String dictionary = null;
		System.out.print("Would like to set a dictionary? Y / N : ");
		if (input.askInput().toUpperCase().equals("N")) {
			return null;
		}
		do {
			printMenuNames("", "Load a dictionary", "Enter a dictionary", "None");
			switch (select(3)) {
			case 0 -> dictionary = loadDictionaryFromFile().getDictionaryString();
			case 1 -> dictionary = inputText("Please enter the words separated with ',' chararcter: ");
			case 2 -> dictionary = back;
			}
			if (dictionary.equals(back)) {
				return null;
			}
		} while (dictionary == null);
		return dictionary;
	}

	private Dictionary loadDictionaryFromFile() throws ExitException {
		Set<String> fileNames = storage.getDictionaryNames();
		if (!fileNames.isEmpty()) {
			printMenuNames("Files available: ", fileNames);
			int choice = select(fileNames.size());
			int count = 0;
			for (String fileName : fileNames) {
				if (count++ == choice) {
					return storage.loadDictionary(fileName.substring(0, fileName.indexOf(".")));
				}
			}
		}
		System.out.println("No text files available.");
		return null;
	}

	private String loadTextFromFile() throws ExitException {
		Set<String> fileNames = storage.getTextNames();
		if (!fileNames.isEmpty()) {
			printMenuNames("Files available: ", fileNames);
			int choice = select(fileNames.size());
			int count = 0;
			for (String fileName : fileNames) {
				if (count++ == choice) {
					return storage.loadText(fileName.substring(0, fileName.indexOf(".")));
				}
			}
		}
		System.out.println("No text files available.");
		return null;
	}

	private String inputText(String prompt) {
		System.out.println(prompt);
		return input.askInput();
	}

	protected String setToString(Set<String> set) {
		return set.toString().replaceAll("[\\[\\]\\s+]", "");
	}

	protected Set<String> stringToSet(String string) {
		return new TreeSet<String>(Arrays.asList(string.replaceAll("[\\[\\]\\s+]", "").split(",")));
	}

}
