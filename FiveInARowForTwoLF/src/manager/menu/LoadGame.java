package manager.menu;

import game.GameProcess;
import manager.Game;

public class LoadGame extends AbstractMenu {

	{
		menuName = "Mentett játék betöltése";
	}

	public void menuOperation() {
		Game game = new GameProcess(SETTINGS);
		game.loadGameFromList();
	}

}
