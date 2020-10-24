package manager.menu;

import manager.Menu;
import manager.MenuServicePoint;

public class MenuFactory implements MenuServicePoint {

	private static final Menu[] MAIN_MENU = new Menu[] {new StartNewGame(), new LoadGame(), new ChangeSettings() };

	public Menu[] getMainMenu() {
		return MAIN_MENU;
	}

}
