package DataAcquisition.SensorData;

import java.util.ArrayList;

public class Sensor{

	private int id;
	private double timeStamp;
	private double temp;
	private ArrayList<TemperatureData> tempData;
	
	public Sensor(int id, ArrayList<TemperatureData> tempData) {
		this.id = id;
		this.tempData = tempData;
	}	
	
	public int getID() {
		return id;
	}
	
	public ArrayList<TemperatureData> getTempData(){
		return tempData;
	}
}
