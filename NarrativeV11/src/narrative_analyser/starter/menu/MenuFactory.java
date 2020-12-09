package narrative_analyser.starter.menu;

import narrative_analyser.starter.Menu;

public class MenuFactory {

	private static final Menu[] MENU_POINTS = { new LoadFromFile(), new StartNewAnalysis() };
	private static final Menu MAIN_MENU = new MainMenu();

	public static Menu[] getAllMenuPoints() {
		return MENU_POINTS;
	}
	
	public static Menu getMainMenu() {
		return MAIN_MENU;
	}

	
}
