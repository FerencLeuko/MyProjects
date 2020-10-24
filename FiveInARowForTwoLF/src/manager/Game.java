package manager;

import storage.FileManager;

public interface Game {
	
	UserInput USER = new UserInput();	
	FileManager FILE = new FileManager();
	public void runGame();
	public void loadGameFromList();

}
