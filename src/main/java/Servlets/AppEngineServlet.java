package Servlets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.json.JSONException;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.socket.SocketServicePb.RemoteSocketServiceError.SystemError;

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
		    datastoreHandler = new DataStoreHandler();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		// Check if file was choosen
		if(!fileInterpreter.hasFile(request)) {
		    response.setContentType("text/plain");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().print("No Textfile chosen.\r\n");
			return;
		}
		
	    // Get Sensors from File
	    ArrayList<Sensor> plainSensors = fileInterpreter.getSensors(request);
	    
	    // Delete leftover Sensors from Datastore
	    datastoreHandler.deleteEntitiesOfKind("Coordinates", datastoreService);
	    
	    // Put new Sensors to Datastore
	    datastoreHandler.putSensorsToDatastore(plainSensors, datastoreService);
	    
	    // Get Sensors from Datastore
	    List<Entity> sensorList = datastoreHandler.getKindFromDatastore("Coordinates", datastoreService);
	    
		// Create String for JSON interface
		String coordinations = "";
		try {
			coordinations = jsonGenerator.createCoordsJSONString(sensorList);
		} catch (JSONException e) {e.printStackTrace();}
		
		System.out.println("POST: Created JSON Coordinates: " + coordinations);
		
	    // Delete leftover coordinateObjects from Datastore
	    datastoreHandler.deleteEntitiesOfKind("JSON", datastoreService);

		// Put JSON to Datastore
	    datastoreHandler.putJsonToDatastore(1, coordinations, datastoreService);
	    
		// Delete leftover Measurements	
	    datastoreHandler.deleteEntitiesOfKind("Measurements", datastoreService);

		int measurementsInDatastore = 0;
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
			measurementsInDatastore = datastoreHandler.putMeasurementsToDatastore(measurementsInDatastore, measurementSensors, datastoreService);
			
		    // Get Measurements from Datastore
			List<Entity> measureList = datastoreHandler.getKindFromDatastore("Measurements", datastoreService);
			
			// Create String for JSON interface
			String measurements = "";
			try {
				measurements = jsonGenerator.createMeasureJSONString(measureList);
			} catch (JSONException e) {e.printStackTrace();}
			
			System.out.println("POST: Created JSON Measurement: " + measurements);
			
			// Put JSON to Datastore
			datastoreHandler.putJsonToDatastore(2, measurements, datastoreService);
			
			// Wait an amount of time			
			System.out.println("AppEngineServlet: Started Sleep: " + timeToSleepInSeconds + " Seconds");
			try {
				TimeUnit.SECONDS.sleep(timeToSleepInSeconds);
			} catch (InterruptedException e) { e.printStackTrace(); }
			System.out.println("AppEngineServlet: Ended Sleep");
		}
	}
}