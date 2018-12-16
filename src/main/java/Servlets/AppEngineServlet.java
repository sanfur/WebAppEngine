package Servlets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import org.json.JSONException;

import DataStorage.*;
import DataVisualization.*;
import DataAcquisition.SensorData.*;
import DataAcquisition.SensorData.Measurement.*;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/finished"}
)
public class AppEngineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private String atHomeFolderPath = "D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\input\\";
	private String atSchoolFolderPathPath = "D:\\CloudComputing\\WebAppEngine\\src\\main\\input\\";
	private String DBFile = "DB.txt";
	private String testFile = "testLocation.txt";
    
	private String atHomeFolderPathJSON = "D:\\Developement\\Github\\Repositories\\WebAppEngine\\src\\main\\webapp\\api\\";
	private String atSchoolFolderPathJSON = "D:\\CloudComputing\\WebAppEngine\\src\\main\\webapp\\api\\";
	private String coordinatesJSONFile = "coordinates.json";
	private String temperaturesJSONFile = "temperature.json";
    
	private int numberOfMeasurements = 9;
	private int timeToSleepInSeconds = 5;
	private ArrayList<Sensor> plainSensors = new ArrayList<Sensor>();
	
	private Locator locator;
	private TempHandler tempHandler;
	private TimeStampCollector timeStampCollector;
	private DataStoreHandler dataStoreHandler;
	private JSONHandler json;
	
	public void init(ServletConfig config) throws ServletException {
		    super.init(config);		    
		    
		    locator = new Locator();
		    tempHandler = new TempHandler();
		    timeStampCollector = new TimeStampCollector();
		    dataStoreHandler = new DataStoreHandler();
		    json = new JSONHandler();
	}
	
 	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	      throws IOException {
	
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().print("No further temperatures to update!\r\n");
	    	 
	    // Delete Datastore
	    dataStoreHandler.deleteDataStore();
	    
	    //	Create Sensors
	    plainSensors = locator.getSensorCoordinates(atHomeFolderPath + testFile);
		System.out.println("AppEngineServlet: Created Sensors: " + plainSensors.size());

		// Put Sensors to Datastore
		for(Sensor sensor : plainSensors) {
			dataStoreHandler.saveSensorToDataStore(sensor);
		}		
		System.out.println("AppEngineServlet: Added Sensors to Datastore");

		// Create JSON-Object for Sensors
		try {
			json.createCoordinatesObject(atHomeFolderPathJSON + coordinatesJSONFile, dataStoreHandler.getCoordinatesFromDataStore());
		} catch (JSONException e1) { e1.printStackTrace(); }
		System.out.println("AppEngineServlet: Generated Coordinate JSON-Object");
		
		// Generate Measurements
	    for(int i = 0; i < numberOfMeasurements; i++) {
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
				dataStoreHandler.saveTemperaturesToDataStore(sensor);
			}
			System.out.println("AppEngineServlet: Added Measurements to Datastore");
			
			// Create JSON-Object for Sensors
			try {
				json.createMeasurementObject(atHomeFolderPathJSON + temperaturesJSONFile, dataStoreHandler.getTemperaturesFromDataStore());
			} catch (JSONException e) { e.printStackTrace(); }	
			System.out.println("AppEngineServlet: Generated Measurement JSON-Object");

			// Wait an amount of time
			
			System.out.println("AppEngineServlet: Started Sleep: " + timeToSleepInSeconds + " Seconds");
			try {
				TimeUnit.SECONDS.sleep(timeToSleepInSeconds);
			} catch (InterruptedException e) { e.printStackTrace(); }
			System.out.println("AppEngineServlet: Ended Sleep");						
		}
 	} 	
}