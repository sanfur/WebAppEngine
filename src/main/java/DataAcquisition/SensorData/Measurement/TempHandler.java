package DataAcquisition.SensorData.Measurement;

import java.util.Random;

public class TempHandler {

	public int getRandomTemp() {		
		Random rand = new Random();
		return rand.nextInt(35);
	}
}
