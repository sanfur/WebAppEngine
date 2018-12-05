package DataAcquisition.SensorData.TemperatureData;

import java.util.Random;

public class TempHandler {

	public double getRandomTemp() {		
		Random rand = new Random();
		return rand.nextInt(35);
	}
}
