package manager.menu;

import manager.Menu;

public abstract class AbstractMenu implements Menu {
	
	String menuName;
	
	public String getMenuName() {
		return menuName;
	}
	
	public abstract void menuOperation();

}
