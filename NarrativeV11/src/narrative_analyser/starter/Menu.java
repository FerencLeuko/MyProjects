package narrative_analyser.starter;

import java.util.Set;

public interface Menu {
	
	String getName();
	
	void menuAction() throws ExitException;
	
	public void printMenuNames(String prompt, Set<String> menuNames);
	
	public int select(int limit) throws ExitException;

}
