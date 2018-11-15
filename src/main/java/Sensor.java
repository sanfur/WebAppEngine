import java.util.Random;

public class Sensor {

	private static int sensorID = 0;

	private int[] averageTemp;
	private int longitude;
	private int latitude;
	private int time;
	
	public Sensor(int numberOfEntries, int longitude, int latitude, int time) {
		averageTemp = new int[numberOfEntries];
		this.longitude = longitude;
		this.latitude = latitude;
		this.time = time;
		sensorID++;
	}	
	
	public int[] getAverageTemp() {
		return averageTemp;
	}
		
	public int getLongitude() {
		return longitude;
	}
	
	public int getLatitude() {
		return latitude;
	}
	
	public int getTime() {
		return time;
	}
	
	public int[] CalculateEntries(int numberOfEntries) {
				
		for(int i = 0; i < numberOfEntries; i++) {
			Random rand = new Random();
			int temp = rand.nextInt(30) + 1;
			averageTemp[i] = temp;
		}
		return averageTemp;
	}
}
