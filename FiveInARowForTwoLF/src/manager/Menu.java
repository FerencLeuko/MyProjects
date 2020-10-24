package manager;

import manager.Settings.FlexibleSettings;

public interface Menu {
	
	String EXIT = Main.EXIT;
	String PROMPT = Main.PROMPT;
	Settings SETTINGS = Settings.getSettings();
	UserInput USER = new UserInput();	
	
	public static FlexibleSettings[] getFlexibleSettings() {
		return SETTINGS.getFlexSettingsArray();
	}
	
	public String getMenuName();
	
	public void menuOperation();
	


}
