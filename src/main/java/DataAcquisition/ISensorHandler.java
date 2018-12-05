package DataAcquisition;

import DataAcquisition.SensorData.*;
import DataAcquisition.SensorData.TemperatureData.TemperatureData;

public interface ISensorHandler {
	
	public GeoSensor getGeoSensor(int id, TemperatureData tempData, Coordinates coordinates);
}
