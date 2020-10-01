package car_wash.main.menu;

import car_wash.register.Materials;
import car_wash.register.Services;

public class ServiceAddition extends AbsMenu {

	{
		menuName = "Szolgáltatás bevitele";
	}

	public void menuAction(Materials materials, Services services) {
		services.addServiceSold();
	}

}
