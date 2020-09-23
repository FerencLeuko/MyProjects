package car_wash_menu;

import car_wash_data.Materials;
import car_wash_data.Services;

public class ServiceAddition extends AbsMenu {

	{
		menuName = "Szolgáltatás bevitele";
	}

	public void menuAction(Materials materials, Services services) {
		services.addServiceSold();
	}

}
