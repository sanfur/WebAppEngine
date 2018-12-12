package DataAcquisition.SensorData.TemperatureData;

public class TemperatureData {

	private long timeStamp;
	private int temperature;
	
	public TemperatureData(long timeStamp, int temperature) {
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
