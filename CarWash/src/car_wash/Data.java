package car_wash;


class Data {
	
	private Materials materials;
	private Services services;
	
	Data (Materials materials, Services services){
		this.materials = materials;
		this.services = services;
	}
	
	Materials getMaterials() {
		return materials;
	}
	
	Services getServices() {
		return services;
	}
	
}
