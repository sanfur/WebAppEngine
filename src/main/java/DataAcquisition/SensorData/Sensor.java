package DataAcquisition.SensorData;

public class Sensor{

	private int sensorID;
	private double lat;
	private double lon; 
	
	public Sensor(int sensorID, double lat, double lon) {
		this.sensorID = sensorID;
		this.lat = lat;
		this.lon = lon;
	}
	
	public int getSensorID() {
		return sensorID;
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLong() {
		return lon;
	}
}
