package DataAcquisition.SensorData;

public class GeoSensor extends Sensor{

	private Coordinates coordinates;
	
	public GeoSensor(int id, double timeStamp, double temp, Coordinates coordinates) {
		super(id, timeStamp, temp);
		this.coordinates = coordinates;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
}
