package car_wash_menu;

import car_wash_register.Materials;
import car_wash_register.Services;

public class ServiceAddition extends AbsMenu {

	{
		menuName = "Szolgáltatás bevitele";
	}

	public void menuAction(Materials materials, Services services) {
		services.addServiceSold();
	}

}
