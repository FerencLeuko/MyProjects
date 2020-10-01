package car_wash_menu;

import car_wash_main.Menu;
import car_wash_register.Materials;
import car_wash_register.Services;

abstract class AbsMenu implements Menu {

	String menuName;

	public String getName() {
		return menuName;
	}

	public abstract void menuAction(Materials materials, Services services);

}
