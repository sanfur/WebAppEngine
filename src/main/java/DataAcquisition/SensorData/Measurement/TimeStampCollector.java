package DataAcquisition.SensorData.Measurement;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TimeStampCollector {
	
	public Long getTimeStamp() {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		return timeStamp.getTime();
	}
}
