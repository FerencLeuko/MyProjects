package car_wash.main.menu;

import car_wash.calculation.RatiosManager;
import car_wash.register.Materials;
import car_wash.register.Services;

public class RatioCalculation extends AbsMenu {
	
	{
		menuName = "Anyagfelhasználási arány pontosítása";
	}

	public void menuAction(Materials materials, Services services) {
		RatiosManager ratios = new RatiosManager();
		ratios.calculateRatio(services);
	}

}
