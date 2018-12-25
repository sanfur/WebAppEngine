package Servlets;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.json.JSONException;
import com.google.appengine.api.datastore.*;

import Utility.*;
import DataAcquisition.*;

@MultipartConfig
@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/appEngineServlet"}
)
public class AppEngineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
	private int numberOfMeasurements = 5;
	private int timeToSleepInSeconds = 3;
	
	private ILocator locator;
	private TempHandler tempHandler;
	private TimeStampCollector timeStampCollector;
	private JSONGenerator jsonGenerator;
	private FileInterpreter fileInterpreter;
	private DataStoreHandler datastoreHandler;
	private com.google.appengine.api.datastore.DatastoreService datastoreService;
	
	public void init(ServletConfig config) throws ServletException {
		    super.init(config);		    
		      
		    locator = new FileLocator();
		    tempHandler = new TempHandler();
		    timeStampCollector = new TimeStampCollector();
		    jsonGenerator = new JSONGenerator();
		    fileInterpreter = new FileInterpreter(locator);
		    datastoreService = DatastoreServiceFactory.getDatastoreService();
		    datastoreHandler = new DataStoreHandler(datastoreService);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
    	/////////////////
    	// Sensors
    	///////////////// 
	    
	    // Get Sensors from File
	    ArrayList<Sensor> plainSensors = fileInterpreter.getSensors(request);
	    
	    // Delete leftover Sensors from Datastore
		Query queryCoordinates = new Query("Coordinates");
		PreparedQuery preparedQueryCoordinates = datastoreService.prepare(queryCoordinates);
		List<Entity> coordinates = preparedQueryCoordinates.asList(FetchOptions.Builder.withDefaults());
		coordinates.forEach(entity -> {datastoreService.delete(entity.getKey());});
	    
	    // Put new Sensors to Datastore
	    ArrayList<Key> coordinatesKeys = new ArrayList<Key>();
	    ArrayList<Key> temperaturesKeys = new ArrayList<Key>();
	    int sensorsInDataStore = 0;
	    for(Sensor sensor : plainSensors) {
			sensorsInDataStore++;
			Key sensorKey = KeyFactory.createKey("Coordinates",sensorsInDataStore);
			Entity currentSensor = new Entity(sensorKey);
			coordinatesKeys.add(sensorKey);
	    	currentSensor.setProperty("SensorID", sensor.getSensorID());
	    	currentSensor.setProperty("Latitude", sensor.getLat());
	    	currentSensor.setProperty("Longitude", sensor.getLong());
	    	datastoreService.put(currentSensor);
	    }
	    
	    // Get Sensors from Datastore
		Query query = new Query("Coordinates");
		PreparedQuery preparedQuery = datastoreService.prepare(query);
		List<Entity> sensorList = preparedQuery.asList(FetchOptions.Builder.withDefaults());		    
	    
		// Create JSONObjectString
		String jsonCoords = "";
		try {
			jsonCoords = jsonGenerator.createCoordsJSONString(sensorList);
		} catch (JSONException e) {e.printStackTrace();}
		
		System.out.println("POST: Created JSON Coordinates: " + jsonCoords);
	    
	    // Delete leftover coordinateObjects from Datastore
		Query queryCoordinatesJSON = new Query("JSON");
		PreparedQuery preparedQueryCoordinatesJSON = datastoreService.prepare(queryCoordinatesJSON);
		List<Entity> coordinatesJSON = preparedQueryCoordinatesJSON.asList(FetchOptions.Builder.withDefaults());
		coordinatesJSON.forEach(entity -> {datastoreService.delete(entity.getKey());});
		
		// Put JSONString to Datastore
		Key coordinatesJSONKey = KeyFactory.createKey("JSON",1);
		Entity jsonEntity = new Entity(coordinatesJSONKey);
		jsonEntity.setProperty("object", jsonCoords);
    	datastoreService.put(jsonEntity);
    	
    	/////////////////
    	// Measurements
    	///////////////// 
    	
		// Delete leftover Measurements		
		Query queryMeasurements = new Query("Measurements");
		PreparedQuery aredQueryMeasurements = datastoreService.prepare(queryMeasurements);
		List<Entity> measurements = aredQueryMeasurements.asList(FetchOptions.Builder.withDefaults());
		measurements.forEach(entity -> {datastoreService.delete(entity.getKey());});
    	
		int measurementsInDataStore = 0;
	    for(int i = 0; i < numberOfMeasurements; i++) {
			// Generate Measurements
	    	ArrayList<MeasurementSensor> measurementSensors = new ArrayList<MeasurementSensor>();
			for(Sensor sensor : plainSensors) {
			    // Create Measurement
		    	long timeStamp = timeStampCollector.getTimeStamp();
		    	int temperature = tempHandler.getRandomTemp();
		    	MeasurementSensor measurementSensor = new MeasurementSensor(
		    			sensor.getSensorID(), 
		    			sensor.getLat(), 
		    			sensor.getLong(), 
		    			timeStamp, 
		    			temperature
				);
		    	measurementSensors.add(measurementSensor);
				System.out.println("AppEngineServlet: Added Measurements to Sensor: " + sensor.getSensorID());
			}
			
			// Put Measurements to Datastore
			for(MeasurementSensor sensor : measurementSensors) {
				measurementsInDataStore++;
				Key sensorKey = KeyFactory.createKey("Measurements", measurementsInDataStore);
				Entity currentSensor = new Entity(sensorKey);
				temperaturesKeys.add(sensorKey);
		    	currentSensor.setProperty("NumberOfMeasurements", measurementsInDataStore);
		    	currentSensor.setProperty("SensorID", sensor.getSensorID());
		    	currentSensor.setProperty("Temperature", sensor.getTemperature());
		    	currentSensor.setProperty("Timestamp", sensor.getTimeStamp());
		    	datastoreService.put(currentSensor);
				
			}
			System.out.println("AppEngineServlet: Added Measurements to Datastore");
			
		    // Get Measurements from Datastore
			Query queryMeasure = new Query("Measurements");
			PreparedQuery preparedQueryMeasure = datastoreService.prepare(queryMeasure);
			List<Entity> measureList = preparedQueryMeasure.asList(FetchOptions.Builder.withDefaults());	
			
			// Create JSONObjectString
			String jsonMeasure = "";
			try {
				jsonMeasure = jsonGenerator.createMeasureJSONString(measureList);
			} catch (JSONException e) {e.printStackTrace();}
			
			System.out.println("POST: Created JSON Measurement: " + jsonMeasure);
			
			// Put JSONString to Datastore
			Key measurementsJSONKey = KeyFactory.createKey("JSON",2);
			Entity jsonEntityM = new Entity(measurementsJSONKey);
			jsonEntityM.setProperty("object", jsonMeasure);
	    	datastoreService.put(jsonEntityM);
			
			// Wait an amount of time			
			System.out.println("AppEngineServlet: Started Sleep: " + timeToSleepInSeconds + " Seconds");
			try {
				TimeUnit.SECONDS.sleep(timeToSleepInSeconds);
			} catch (InterruptedException e) { e.printStackTrace(); }
			System.out.println("AppEngineServlet: Ended Sleep");
		}
	}
}