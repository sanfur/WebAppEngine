package DataAcquisition.SensorData.TemperatureData;

public class TemperatureData {

	private long timeStamp;
	private double temperature;
	
	public TemperatureData(long timeStamp, double temperature) {
		this.timeStamp = timeStamp;
		this.temperature = temperature;
	}
	
	public long getTimeStamp() {
		return timeStamp;	
	}
	
	public double getTemperature() {
		return temperature;	
	}	
}
