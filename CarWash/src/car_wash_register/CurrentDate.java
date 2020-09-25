package car_wash_data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {

	public Date getCurrentDate() {
		return new Date();
	}

	public String provideDateString() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd - HH:mm:ss ");
		return formatter.format(getCurrentDate());
	}

}
