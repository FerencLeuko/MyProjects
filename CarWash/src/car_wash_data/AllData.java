package car_wash_data;

public class AllData {
	
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
