package car_wash.main.menu;

import car_wash.main.Menu;
import car_wash.register.Materials;
import car_wash.register.Services;

abstract class AbsMenu implements Menu {

	String menuName;

	public String getName() {
		return menuName;
	}

	public abstract void menuAction(Materials materials, Services services);

}
