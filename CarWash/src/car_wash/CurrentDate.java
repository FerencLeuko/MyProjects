package car_wash;

import java.text.SimpleDateFormat;
import java.util.Date;

class CurrentDate {
		
	Date getCurrentDate(){
		return new Date();
	}

	String provideDateString() {
	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd - HH:mm:ss ");
	return formatter.format(getCurrentDate());	
	}
	
}
