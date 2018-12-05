package DataAcquisition.SensorData.TemperatureData;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TimeStampCollector {
	
	public Long getTimeStamp() {
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		return timeStamp.getTime();
	}
	
//	public ArrayList<Long> getWeeklyTimeTemps(int numberOfStamps) {
//		ArrayList<Long> stamps = new ArrayList<Long>();
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        System.out.println(timestamp.getTime());
//        long time = timestamp.getTime();
//        stamps.add(time);
//        for(int i = 0; i < numberOfStamps - 1; i++) {        	        	
//        	time += (3600 * 24 * 7);
//        	stamps.add(time);
//        }
//        return stamps;
//	}
}
