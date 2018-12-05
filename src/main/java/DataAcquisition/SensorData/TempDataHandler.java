package DataAcquisition.SensorData;

import java.sql.Timestamp;
import java.util.ArrayList;

import DataAcquisition.SensorData.TemperatureData.TempHandler;
import DataAcquisition.SensorData.TemperatureData.TemperatureData;

public class TempDataHandler {

	
	public ArrayList<TemperatureData> GetTempData(int numberOfStamps){
		
		ArrayList<TemperatureData> stamps = new ArrayList<TemperatureData>();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TempHandler temp = new TempHandler();
		long time = timestamp.getTime();
        TemperatureData tempData = new TemperatureData(time, temp.getRandomTemp());
        stamps.add(tempData);
        for(int i = 0; i < numberOfStamps - 1; i++) {        	 
        	time += 3600;
            TemperatureData tempData2 = new TemperatureData(time , temp.getRandomTemp());
        	stamps.add(tempData2);	
    	}
        return stamps;
	}
}
