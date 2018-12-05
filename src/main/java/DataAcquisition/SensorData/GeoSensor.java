package DataAcquisition.SensorData;

import DataAcquisition.SensorData.TemperatureData.TemperatureData;

public class GeoSensor extends Sensor{

	private Coordinates coordinates;	
	
	public GeoSensor(int id, TemperatureData tempData, Coordinates coordinates) {
		super(id, tempData);
		this.coordinates = coordinates;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
}
