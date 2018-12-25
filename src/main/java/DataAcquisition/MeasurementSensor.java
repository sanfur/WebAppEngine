package DataAcquisition;

public class MeasurementSensor extends Sensor{

	private long timeStamp;
	private int temperature;
	
	public MeasurementSensor(int sensorID, double lat, double lon, long timeStamp, int temperature) {
		super(sensorID, lat, lon);
		this.timeStamp = timeStamp;
		this.temperature = temperature;
	}
	
	public long getTimeStamp() {
		return timeStamp;	
	}
	
	public int getTemperature() {
		return temperature;	
	}
}
