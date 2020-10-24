package car_wash.register;

import car_wash.main.Register;

public class AllData implements Register {
	
	private Materials materials;
	private Services services;
	
	public AllData (Materials materials, Services services){
		this.materials = materials;
		this.services = services;
	}
	
	public Materials getMaterials() {
		return materials;
	}
	
	public Services getServices() {
		return services;
	}
	
	public String dateString() {
		return new CurrentDate().provideDateString();
	}
	
}
