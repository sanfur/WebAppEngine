package DataAcquisition;

import java.sql.Timestamp;

public class TimeStampCollector {
	
	public Long getTimeStamp() {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		return timeStamp.getTime();
	}
}
