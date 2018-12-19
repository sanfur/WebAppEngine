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
import Management.MeasurementHandler;
import Management.SensorHandler;
import DataAcquisition.SensorData.*;
import DataAcquisition.SensorData.Measurement.*;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/finished"}
)
public class AppEngineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private String DBFile = "/input/DB.txt";
	private String testFile = "/input/testLocation.txt";
	private String coordinatesJSONFile = "/api/coordinates.json";
	private String temperaturesJSONFile = "/api/temperature.json";
    
	private int numberOfMeasurements = 15;
	private int timeToSleepInSeconds = 3;
	private ArrayList<Sensor> plainSensors;
	
	private ILocator locator;
	private TempHandler tempHandler;
	private TimeStampCollector timeStampCollector;
	private DataStoreHandler dataStoreHandler;
	private JSONGenerator jsonGenerator;
	
	public void init(ServletConfig config) throws ServletException {
		    super.init(config);		    
		    
		    locator = new ShowLocator();
		    tempHandler = new TempHandler();
		    timeStampCollector = new TimeStampCollector();
		    dataStoreHandler = new DataStoreHandler();
		    jsonGenerator = new JSONGenerator();
		    
			// TODO; Local path
			coordinatesJSONFile = getServletContext().getRealPath(coordinatesJSONFile);	
			temperaturesJSONFile = getServletContext().getRealPath(temperaturesJSONFile);
//	        testFile = getServletContext().getRealPath(testFile);
	}
	
 	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	      throws IOException {
       
 		response.setIntHeader("Refresh", 5);
	    response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().print("No further temperatures to update!\r\n");
	    
	    // Delete Datastore
	    dataStoreHandler.deleteDataStore();
	    	    
	    locator.setFilePath(testFile);
	    
	    // Send Sensors to Datastore and Browser
	    SensorHandler sensorHandler = new SensorHandler(locator, dataStoreHandler, jsonGenerator);
	    plainSensors = sensorHandler.prepareSensors(coordinatesJSONFile);
	    
	    // Send Measurement to Datastore and Browser
	    MeasurementHandler measurementHandler = new MeasurementHandler(plainSensors, dataStoreHandler, jsonGenerator);
	    
	    for(int i = 0; i < numberOfMeasurements; i++) {
	    	measurementHandler.prepareMeasurements(timeStampCollector, tempHandler, temperaturesJSONFile);
	    
			// Wait an amount of time			
			System.out.println("AppEngineServlet: Started Sleep: " + timeToSleepInSeconds + " Seconds");
			try {
				TimeUnit.SECONDS.sleep(timeToSleepInSeconds);
			} catch (InterruptedException e) { e.printStackTrace(); }
			System.out.println("AppEngineServlet: Ended Sleep");
	    }
 	} 	
}