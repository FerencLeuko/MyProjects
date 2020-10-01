package car_wash_register;

import java.text.SimpleDateFormat;
import java.util.Date;

import car_wash_main.Register;

public class CurrentDate implements Register {

	public Date getCurrentDate() {
		return new Date();
	}

	public String provideDateString() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd - HH:mm:ss ");
		return formatter.format(getCurrentDate());
	}

}
