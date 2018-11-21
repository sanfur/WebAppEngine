package Client.SensorData;
public class Sensor{

	private int id;
	private double timeStamp;
	private double temp;
	
	public Sensor(int id, double timeStamp, double temp) {
		this.id = id;
		this.timeStamp = timeStamp;
		this.temp = temp;
	}	
	
	public int getID() {
		return id;
	}
	
	public double getTimeStamp() {
		return timeStamp;
	}
	
	public double getTemp() {
		return temp;
	}	
}
