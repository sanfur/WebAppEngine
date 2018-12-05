package DataAcquisition.SensorData;

import DataAcquisition.SensorData.TemperatureData.TemperatureData;

public class Sensor{

	private int id;
	private TemperatureData tempWithTimeData;
	
	public Sensor(int id, TemperatureData tempWithTimeData) {
		this.id = id;
		this.tempWithTimeData = tempWithTimeData;
	}
	
	public int getID() {
		return id;
	}
	
	public TemperatureData getTempWithTime() {
		return tempWithTimeData;
	}
}
