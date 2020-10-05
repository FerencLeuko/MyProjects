package car_wash.main;

import car_wash.register.Materials;
import car_wash.register.Services;

public interface Menu {
	
	String EXTRAS_NAME = "Extrák";
	FileManager FILE = FileManager.getFileManager();
	
	public String getName();
	
	public void menuAction(Materials materials, Services services);

}
