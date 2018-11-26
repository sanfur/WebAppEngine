package DataStorage;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.*;
import DataAcquisition.SensorData.*;

public class DataStoreHandler {
	
	private ArrayList<GeoSensor> sensors;
	private ArrayList<Key> sensorKeys;

	public DataStoreHandler(ArrayList<GeoSensor> sensors) {
		this.sensors = sensors;
		sensorKeys = new ArrayList<Key>();
	}

	public void saveToDataStore() {
    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		for(GeoSensor sensor : sensors) {
			Key sensorKey = KeyFactory.createKey("Sensor", "Sensor" + sensor.getID());
			Entity currentSensor = new Entity(sensorKey);
			sensorKeys.add(sensorKey);
	    	currentSensor.setProperty("ID", sensor.getID());
	    	currentSensor.setProperty("Temperature", sensor.getTemp());
	    	currentSensor.setProperty("Latitude", sensor.getCoordinates().getLat());
	    	currentSensor.setProperty("Longitude", sensor.getCoordinates().getLong());
	    	currentSensor.setProperty("Time", sensor.getTimeStamp());	    	
	    	datastore.put(currentSensor);
		}		
	}
	
	public List<Entity> getFromDataStore() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();		
		Query query = new Query("Sensor");
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> list = preparedQuery.asList(FetchOptions.Builder.withDefaults());	
		return list;
	}
	
	public void deleteDataStore() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
		for(Key sensorKey : sensorKeys) {
			datastore.delete(sensorKey);
		}
		System.out.println("SUCCESS DataStoreHandler: Datastore cleared.");
	}
}
