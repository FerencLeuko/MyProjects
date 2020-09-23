package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;
import car_wash_main.Menu;

abstract class AbsMenu implements Menu {

	String menuName;
	
	public String getName() {
		return menuName;
	}

	public abstract void menuAction(Materials materials, Services services);
		// TODO Auto-generated method stub
	
	

}
