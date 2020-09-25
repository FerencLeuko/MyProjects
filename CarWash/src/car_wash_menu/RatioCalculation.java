package car_wash_menu;

import car_wash_calculation.RatiosManager;
import car_wash_data.Materials;
import car_wash_data.Services;

public class Ratio extends AbsMenu {
	
	{
		menuName = "Anyagfelhasználási arány pontosítása";
	}

	public void menuAction(Materials materials, Services services) {
		RatiosManager ratios = new RatiosManager();
		ratios.calculateRatio(services);
	}

}
