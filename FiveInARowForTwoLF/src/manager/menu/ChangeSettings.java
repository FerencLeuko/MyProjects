package manager.menu;

import manager.AmobaSettingsException;
import manager.Menu;
import manager.Settings.FlexibleSettings;

public class ChangeSettings extends AbstractMenu {

	private FlexibleSettings[] flexSets = Menu.getFlexibleSettings();
	private boolean userExit;

	{
		menuName = "Beállítások";
	}

	public void menuOperation() {
		processUserInput();
	}

	private void processUserInput() {
		do {
			userExit = false;
			printSettingsSelection();
			selectAndChange();
		} while (!userExit);
	}

	private void printSettingsSelection() {
		System.out.println();
		int counter = 1;
		for (FlexibleSettings flexSet : flexSets) {
			System.out.printf("%d%s%s%n", counter++, ". ", flexSet.getName());
		}
		System.out.printf("%s Vissza a főmenühöz%n", EXIT);
	}

	private void selectAndChange() {
		do {
			System.out.print(PROMPT);
			int index;
			String choice = USER.askInput();
			if (choice.equals(EXIT)) {
				userExit = true;
			} else {
				try {
					index = Integer.parseInt(choice) - 1;
					if (index < 0 || index >= flexSets.length) {
						continue;
					}
				} catch (RuntimeException e) {
					continue;
				}
				changeValue(index);
				break;
			}
		} while (!userExit);
	}

	private void changeValue(int index) {
		try {
			System.out.printf("%n%s%s%s", "A jelenlegi érték: ", valueToPrint(flexSets[index].getValue()), askNewvalue(flexSets[index].getValue()));
			flexSets[index].setValue(Integer.parseInt(USER.askInput()));
		} catch (AmobaSettingsException e) {
			System.out.println(e.getMessage());
		} catch (RuntimeException ex) {
			System.out.println("Hibás adatbevitel.");
		}
		System.out.printf("%s%s%n", "A beállított érték: ", valueToPrint(flexSets[index].getValue()));
	}

	private String valueToPrint(int value) {
		if (value == 1) {
			return "Igen";
		} else if (value == 2) {
			return "Nem";
		} else
			return "" + value;
	}

	private String askNewvalue(int value) {
		return (value == 1 || value == 2) ? ". Az új érték (1=igen 2=nem) > " : ". Az új érték > ";
	}

}
