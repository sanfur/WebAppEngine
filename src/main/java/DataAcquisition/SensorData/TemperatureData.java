package DataAcquisition.SensorData;

public class TemperatureData {

	private int timeStamp;
	private double temperature;
	
	public TemperatureData(int timeStamp, double temperature) {
		this.timeStamp = timeStamp;
		this.temperature = temperature;
	}
	
	public int getTimeStamp() {
		return timeStamp;	
	}
	
	public double getTemperature() {
		return temperature;	
	}	
}
