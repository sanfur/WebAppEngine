package Client.SensorData;

public class GeoSensor extends Sensor{

	private double latitude;
	private double longitude;
	
	public GeoSensor(int id, double timeStamp, double temp, double latitude, double longitude) {
		super(id, timeStamp, temp);
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}

}
