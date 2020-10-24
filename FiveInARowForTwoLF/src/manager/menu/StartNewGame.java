package manager.menu;

import game.GameProcess;
import manager.Game;

public class StartNewGame extends AbstractMenu {
	
	{
		menuName = "Új játék indítása";
	}

	public void menuOperation() {
		System.out.printf("%nÚj játék indult.%n%n");
		Game game = new GameProcess(SETTINGS);
		game.runGame();
	}


}
