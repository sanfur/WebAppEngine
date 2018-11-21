package Client;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import Client.SensorData.Sensor;

public class SaveToDataStore {
	
	private Sensor sensor;
	
	public SaveToDataStore(Sensor sensor) {
	}
	
	public void execute() {
		
		Entity currentSensor = new Entity("SensorA", "Sensor");
    	currentSensor.setProperty("Temperature", sensor.getTemp());
    	currentSensor.setProperty("Longitude", 9.53287);
    	currentSensor.setProperty("Latitude", 46.84986);
    	currentSensor.setProperty("Time", sensor.getTimeStamp());
    	
    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    	datastore.put(currentSensor);
    	
    	
	}
}
