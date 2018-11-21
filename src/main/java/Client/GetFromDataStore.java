package Client;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class GetFromDataStore {
	
	public void execute() {
		
		Key sensorKey = KeyFactory.createKey("Sensor", "SensorA");		 
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity  sensor;
	  
	}
}
