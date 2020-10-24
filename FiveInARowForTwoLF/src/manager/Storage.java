package manager;


public interface Storage {
	
	public String getFileNames();

	public void loadGameFromList();
	
	public void saveGame(String fileName);
	
	

}
