package car_wash_menu;

import car_wash_main.Menu;
import car_wash_main.MenuSelection;

public class MenuFactory implements MenuSelection {

	private final Menu[] menuSelection = { new ServiceAddition(), new MaterialOrder(), new StorageUpdate(), new PrintConsumption(),
			new PrintAllConsumption(), new SaveAs(), new LoadAs(), new NewSession(), new Extras() };

	private final Menu[] extraSelection = { new RatioCalculation(), new Sum(), new ChangePrice(), new ChangeUnit(), new DefaultPrice(), new DefaultRatio() };

	public static MenuFactory getInstance() {
		return new MenuFactory();
	}
	
	public Menu[] getMenuSelection() {
		return menuSelection;
	}
	
	public Menu[] getExtraSelection() {
		return extraSelection;
	}
}
