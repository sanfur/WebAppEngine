package DataAcquisition;

import DataAcquisition.SensorData.*;
import DataAcquisition.SensorData.TemperatureData.TemperatureData;

public class SensorHandler implements ISensorHandler{
	
	public GeoSensor getGeoSensor(int id, TemperatureData tempData, Coordinates coordinates) {
		return  new GeoSensor(id, tempData, coordinates);
	}
}
