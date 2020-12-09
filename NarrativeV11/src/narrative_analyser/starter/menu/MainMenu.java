package narrative_analyser.starter.menu;

import java.util.LinkedHashSet;
import java.util.Set;

import narrative_analyser.starter.ExitException;
import narrative_analyser.starter.Menu;

class MainMenu extends AbstractMenu {

	{
		name = "Main menu";
	}

	public void printMenu() {
		Set<String> menuNames = new LinkedHashSet<>();
		for (Menu menu : allMenuPoints) {
			menuNames.add(menu.getName());
		}
		printMenuNames(String.format("%n%s", "Main menu: "), menuNames);
	}

	@Override
	public void menuAction() {
		printMenu();
		int choice = -1;
		try {
			try {
				choice = select(allMenuPoints.length);
			} catch (ExitException e) {
				return;
			}
			int count = 0;
			for (Menu menu : allMenuPoints) {
				if (count++ == choice) {
					menu.menuAction();
				}
			}
		} catch (ExitException e) {
			menuAction();
		}
	}

}
