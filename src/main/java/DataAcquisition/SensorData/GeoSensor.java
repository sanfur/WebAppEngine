package DataAcquisition.SensorData;

import java.util.ArrayList;

public class GeoSensor extends Sensor{

	private Coordinates coordinates;
	
	public GeoSensor(int id, ArrayList<TemperatureData> tempData, Coordinates coordinates) {
		super(id, tempData);
		this.coordinates = coordinates;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
}
